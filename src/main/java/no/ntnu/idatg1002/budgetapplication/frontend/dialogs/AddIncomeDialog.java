package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ExceptionAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;

/**
 * Represents a custom dialog for adding an income in the budget application. The dialog includes
 * fields for entering income details, such as amount, description, recurring type, and income
 * category.
 *
 * @author Erik Bjørnsen, Eskil Alstad
 * @version 2.0
 */
public class AddIncomeDialog extends Dialog<Income> {

  @FXML public Button submitButton;
  @FXML private TextField incomeAmountField;
  @FXML private TextField incomeDescriptionField;
  @FXML private ComboBox<String> incomeCategoryComboBox;
  @FXML private ComboBox<String> recurringIntervalComboBox;
  @FXML private DatePicker incomeDatePicker;
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
  }


  /** Closes the dialog when the "Cancel" button is clicked. */
  @FXML
  private void closeDialog() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Handles the "Submit" button click, creating a new income if all fields are valid. Displays an
   * exception alert or dynamic feedback if there's an error or fields are invalid.
   */
  @FXML
  private void handleSubmit(ActionEvent actionEvent) {
    Income newIncome;
    if (assertAllFieldsValid()) {
      try {
        newIncome =
            new Income(
                Integer.parseInt(getIncomeAmountFieldText()),
                getIncomeDescriptionFieldText(),
                getRecurringIntervalComboBoxValue(),
                getIncomeCategoryComboBoxValue(),
                getIncomeDateValue());
        this.setResult(newIncome);
        this.close();
      } catch (Exception exception) {
        ExceptionAlert exceptionAlert = new ExceptionAlert(exception);
        exceptionAlert.showAndWait();
      }
    } else {
      generateDynamicFeedbackAlert();
    }
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

  private void configureIncomeDatePicker() {
    incomeDatePicker
        .focusedProperty()
        .addListener(
            (observableValue, oldPropertyValue, newPropertyValue) -> {
              if (Boolean.TRUE.equals(newPropertyValue)) {
                incomeDatePicker.show();
              } else {
                incomeDatePicker.hide();
              }
            });
  }

  /**
   * Returns the text from the income description input field.
   *
   * @return the description of the income
   */
  private String getIncomeDescriptionFieldText() {
    return incomeDescriptionField.getText();
  }

  /**
   * Returns the text from the income amount input field.
   *
   * @return the amount of the income
   */
  private String getIncomeAmountFieldText() {
    return incomeAmountField.getText();
  }

  /**
   * Returns the selected value from the recurring interval combo box.
   *
   * @return the selected recurring type of the income
   */
  private RecurringType getRecurringIntervalComboBoxValue() {
    return RecurringType.valueOfLabel(recurringIntervalComboBox.getValue());
  }

  @FXML
  private LocalDate getIncomeDateValue() {
    if (incomeDatePicker.getValue() == null) {
      incomeDatePicker.setValue(LocalDate.now());
    }
    LocalDate date = incomeDatePicker.getValue();
    return date;
  }

  /**
   * Returns the selected value from the income category combo box.
   *
   * @return the selected income category of the income
   */
  private IncomeCategory getIncomeCategoryComboBoxValue() {
    return IncomeCategory.valueOfLabel(incomeCategoryComboBox.getValue());
  }

  /**
   * Verifies that all input fields have valid values.
   *
   * @return true if all input fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!getIncomeAmountFieldText().isEmpty()
        && !getIncomeAmountFieldText().isBlank()
        && !getIncomeDescriptionFieldText().isEmpty()
        && !getIncomeDescriptionFieldText().isBlank()
        && getRecurringIntervalComboBoxValue() != null
        && getIncomeCategoryComboBoxValue() != null
        && getIncomeDateValue() != null
        && !getIncomeDateValue().isBefore(
        SessionAccount.getInstance().getAccount().getSelectedBudget()
            .getStartDate())
        && !getIncomeDateValue().isAfter(SessionAccount.getInstance().getAccount().getSelectedBudget()
        .getEndDate()));
  }

  /**
   * Generates an alert that gives feedback to the user of what fields still needs to be filled out.
   *
   * <p>The following fields are checked for completeness:
   *
   * <ul>
   *   <li>Amount
   *   <li>Description
   *   <li>Recurring interval
   *   <li>Category
   * </ul>
   */
  private void generateDynamicFeedbackAlert() {
    WarningAlert warningAlert = new WarningAlert();

    StringBuilder builder = new StringBuilder("Please fill out the following field(s): \n");

    if (getIncomeAmountFieldText().isEmpty() || getIncomeAmountFieldText().isBlank()) {
      builder.append("Amount \n");
    }
    if (getIncomeDescriptionFieldText().isEmpty() || getIncomeDescriptionFieldText().isBlank()) {
      builder.append("Description \n");
    }
    if (getRecurringIntervalComboBoxValue() == null) {
      builder.append("Recurring interval \n");
    }
    if (getIncomeCategoryComboBoxValue() == null) {
      builder.append("Category \n");
    }
    if (getIncomeDateValue() == null) {
      builder.append("Income date added \n");
    }
    if (getIncomeDateValue().isBefore(SessionAccount.getInstance().getAccount().getSelectedBudget()
        .getStartDate())
        || getIncomeDateValue().isAfter(
        SessionAccount.getInstance().getAccount().getSelectedBudget()
            .getEndDate())) {
      builder.append("Income date need to be inside budget period: \n");
      builder.append(SessionAccount.getInstance().getAccount().getSelectedBudget()
          .getStartToEndString());

      warningAlert.setContentText(builder.toString());
      warningAlert.initOwner(this.getDialogPane().getScene().getWindow());
      warningAlert.showAndWait();
    }
  }

  @FXML
  public void initialize() {
    recurringIntervalComboBox.getItems().addAll(RecurringType.labelValues());
    incomeCategoryComboBox.getItems().addAll(IncomeCategory.labelValues());
    configureIncomeAmountField();
    configureIncomeDescriptionField();
    configureIncomeDatePicker();
  }
}
