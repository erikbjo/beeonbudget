package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarPicker;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ExceptionAlert;

/**
 * Represents a custom dialog for adding a budget in the budget application. The dialog includes a
 * field for entering the budget name.
 *
 * @author Erik Bjørnsen
 * @version 1.1
 */
public class AddBudgetDialog extends Dialog<Budget> {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private Label budgetNameText;
  @FXML private TextField budgetNameTextField;
  @FXML private Button cancelButton;
  @FXML private Button submitButton;
  @FXML private DatePicker endDatePicker;
  @FXML private DatePicker startDatePicker;

  /** Constructs an AddBudgetDialog, loading the FXML and configuring the budget name text field. */
  public AddBudgetDialog() {
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

  /** Configures the budget name text field to prevent starting with a space. */
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

  /**
   * Closes the AddBudgetDialog when the "Cancel" button is clicked.
   *
   * @param event the action event triggered by clicking the "Cancel" button
   */
  @FXML
  void closeDialog(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Handles the "Submit" button click, creating a new budget if all fields are valid. Displays
   * dynamic feedback if the fields are invalid.
   *
   * @param event the action event triggered by clicking the "Submit" button
   */
  @FXML
  void handleSubmit(ActionEvent event) {
    Budget newBudget;
    if (assertAllFieldsValid()) {
      try {
        newBudget = new Budget(budgetNameTextField.getText());
        this.setResult(newBudget);
        this.close();
      } catch (Exception exception) {
        ExceptionAlert exceptionAlert = new ExceptionAlert(exception);
        exceptionAlert.showAndWait();
      }
    } else {
      generateFeedbackAlert();
    }
  }

  /**
   * Checks if all fields in the dialog are valid for budget creation.
   *
   * @return true if all fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return !budgetNameTextField.getText().isEmpty() && !budgetNameTextField.getText().isBlank();
  }


  /** Generates feedback for the user if the budget name field is invalid. */
  private void generateFeedbackAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Please fill out the budget name");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(this.getDialogPane().getScene().getWindow());
    alert.showAndWait();
  }

  public DatePicker getEndDatePicker() {
    return endDatePicker;
  }

  public DatePicker getStartDatePicker() {
    return startDatePicker;
  }

  @FXML
  public LocalDate getStartDate(ActionEvent event){
      LocalDate startDate = getStartDatePicker().getValue();
      return startDate;
  }

  @FXML
  public LocalDate getEndDate(ActionEvent event) {
    LocalDate endDate = getEndDatePicker().getValue();
    return endDate;
  }
}
