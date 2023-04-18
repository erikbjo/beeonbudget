package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

/**
 * Represents a custom dialog for adding an income in the budget application. The dialog includes
 * fields for entering income details, such as amount, description, recurring type, and income
 * category.
 *
 * @author Erik Bjørnsen, Eskil Alstad
 * @version 1.2
 */
public class AddIncomeDialog extends Dialog<Income> {
  private Income newIncome;

  @FXML private TextField incomeAmountField;
  @FXML private TextField incomeDescriptionField;
  @FXML private ComboBox<IncomeCategory> incomeCategoryComboBox;
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;
  @FXML private Button cancelButton;

  /**
   * Constructs an AddIncomeDialog, setting up the user interface components and necessary input
   * validation.
   */
  public AddIncomeDialog() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
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
    this.setTitle("Add Income");

    // adds enums to combo boxes
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
    incomeCategoryComboBox.getItems().addAll(IncomeCategory.values());

    configureIncomeAmountField();
    configureIncomeDescriptionField();
  }

  /**
   * Configures the incomeAmountField to only accept numeric input. If a non-numeric character is
   * entered, it is removed from the input.
   */
  private void configureIncomeAmountField() {
    incomeAmountField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                incomeAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });
  }

  /**
   * Configures the incomeDescriptionField to prevent input from starting with a space. If a space
   * is entered at the beginning of the input, it is removed.
   */
  private void configureIncomeDescriptionField() {
    incomeDescriptionField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                incomeDescriptionField.clear();
              }
            });
  }

  /**
   * Returns the text from the income description input field.
   *
   * @return the description of the income
   */
  private String getIncomeDescriptionField() {
    return incomeDescriptionField.getText();
  }

  /**
   * Returns the text from the income amount input field.
   *
   * @return the amount of the income
   */
  private String getIncomeAmountField() {
    return incomeAmountField.getText();
  }

  /**
   * Returns the selected value from the recurring interval combo box.
   *
   * @return the selected recurring type of the income
   */
  private RecurringType getRecurringIntervalComboBox() {
    return recurringIntervalComboBox.getValue();
  }

  /**
   * Returns the selected value from the income category combo box.
   *
   * @return the selected income category of the income
   */
  private IncomeCategory getIncomeCategoryComboBox() {
    return incomeCategoryComboBox.getValue();
  }

  /**
   * Verifies that all input fields have valid values.
   *
   * @return true if all input fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (incomeDescriptionField.getText() != null
        && incomeAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && incomeCategoryComboBox.getValue() != null);
  }

  @FXML
  private void closeDialog() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void handleSubmit() {
    if (assertAllFieldsValid()) {
      newIncome =
          new Income(
              Integer.parseInt(getIncomeAmountField()),
              getIncomeDescriptionField(),
              getRecurringIntervalComboBox(),
              getIncomeCategoryComboBox());
      this.setResult(newIncome);
      this.close();
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("Please fill out all fields in dialog");
      alert.initModality(Modality.NONE);
      alert.initOwner(this.getDialogPane().getScene().getWindow());
      alert.showAndWait();
    }
  }
}
