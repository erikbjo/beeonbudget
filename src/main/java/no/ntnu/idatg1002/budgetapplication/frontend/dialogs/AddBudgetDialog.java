package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

public class AddBudgetDialog extends Dialog<Budget> {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private Label budgetNameText;
  @FXML private TextField budgetNameTextField;
  @FXML private Button cancelButton;
  @FXML private Button submitButton;

  public AddBudgetDialog() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addBudgetDialog.fxml"));
    loader.setController(this);
    DialogPane dialogPane = new DialogPane();
    try {
      dialogPane.setContent(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/dialog.css"))
            .toExternalForm();
    dialogPane.getStylesheets().add(css);

    this.setDialogPane(dialogPane);
    this.setTitle("Add Budget");

    configureBudgetNameAmountField();
  }

  private void configureBudgetNameAmountField() {
    budgetNameTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                budgetNameTextField.clear();
              }
            });
  }

  @FXML
  void closeDialog(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  void handleSubmit(ActionEvent event) {
    Budget newBudget;
    if (assertAllFieldsValid()) {
      newBudget = new Budget(budgetNameTextField.getText());
      this.setResult(newBudget);
      this.close();
    } else {
      generateDynamicFeedbackAlert();
    }
  }

  private boolean assertAllFieldsValid() {
    boolean nameValid =
        SessionAccount.getInstance().getAccount().getBudgets().stream()
            .noneMatch(
                budget -> Objects.equals(budget.getBudgetName(), budgetNameTextField.getText()));

    return !budgetNameTextField.getText().isEmpty() && nameValid;
  }

  private void generateDynamicFeedbackAlert() {
    System.out.println("placeholder");
  }
}
