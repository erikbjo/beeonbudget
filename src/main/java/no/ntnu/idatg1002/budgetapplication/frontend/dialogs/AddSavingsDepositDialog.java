package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ExceptionAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;

/**
 * Represents a custom dialog for adding an expense in the budget application. The dialog includes
 * fields for entering expense details, such as amount, description, recurring type, and expense
 * category.
 *
 * @author Erik Bjørnsen, Eskil Alstad
 * @version 2.0
 */
public class AddSavingsDepositDialog extends Dialog<Integer> {

  @FXML public Button submitButton;
  @FXML private TextField depositAmountField;
  @FXML private TextField depositDescriptionField;
  @FXML private ComboBox<String> recurringIntervalComboBox;
  @FXML private DatePicker depositDatePicker;
  @FXML private Button cancelButton;
  /**
   * Constructs an AddExpenseDialog, setting up the user interface components and necessary input
   * validation.
   */
  public AddSavingsDepositDialog() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addSavingsDepositDialog.fxml"));
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
    this.setTitle("Deposit");
  }

  /** Closes the dialog when the "Cancel" button is clicked. */
  @FXML
  private void closeDialog() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Handles the "Submit" button click, creating a new expense if all fields are valid. Displays an
   * exception alert or dynamic feedback if there's an error or fields are invalid.
   */
  @FXML
  private void handleSubmit(ActionEvent actionEvent) {
    int newDeposit;
    if (assertAllFieldsValid()) {
      try {
        newDeposit =
                Integer.parseInt(getDepositAmountFieldText());
        this.setResult(newDeposit);
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
   * Configures the expenseAmountField to only accept numeric input. If a non-numeric character is
   * entered, it is removed from the input.
   */
  private void configureDepositAmountField() {
    depositAmountField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                depositAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });
  }

  /**
   * Configures the expenseDescriptionField to prevent input from starting with a space. If a space
   * is entered at the beginning of the input, it is removed.
   */
  private void configureDepositDescriptionField() {
    depositDescriptionField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                depositDescriptionField.clear();
              }
            });
  }

  private void configureDepositDatePicker() {
    depositDatePicker
        .focusedProperty()
        .addListener(
            (observableValue, oldPropertyValue, newPropertyValue) -> {
              if (Boolean.TRUE.equals(newPropertyValue)) {
                depositDatePicker.show();
              } else {
                depositDatePicker.hide();
              }
            });
  }

  /**
   * Returns the text from the expense description input field.
   *
   * @return the description of the expense
   */
  private String getDepositDescriptionFieldText() {
    return depositDescriptionField.getText();
  }

  /**
   * Returns the text from the expense amount input field.
   *
   * @return the amount of the expense
   */
  private String getDepositAmountFieldText() {
    return depositAmountField.getText();
  }

  /**
   * Returns the selected value from the recurring interval combo box.
   *
   * @return the selected recurring type of the expense
   */
  private RecurringType getRecurringIntervalComboBoxValue() {
    return RecurringType.valueOfLabel(recurringIntervalComboBox.getValue());
  }

  private LocalDate getDepositDateValue() {
    if (depositDatePicker.getValue() == null) {
      depositDatePicker.setValue(LocalDate.now());
    }
    return depositDatePicker.getValue();
  }

  /**
   * Verifies that all input fields have valid values.
   *
   * @return true if all input fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!getDepositAmountFieldText().isEmpty()
        && !getDepositAmountFieldText().isBlank()
        && !getDepositDescriptionFieldText().isEmpty()
        && !getDepositDescriptionFieldText().isBlank()
        && getRecurringIntervalComboBoxValue() != null
        && getDepositDateValue() != null
        && !getDepositDateValue().isBefore(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
        .getStartDate())
        && !getDepositDateValue().isAfter(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
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

    if (getDepositAmountFieldText().isEmpty() || getDepositAmountFieldText().isBlank()) {
      builder.append("Amount \n");
    }
    if (getDepositDescriptionFieldText().isEmpty() || getDepositDescriptionFieldText().isBlank()) {
      builder.append("Description \n");
    }
    if (getRecurringIntervalComboBoxValue() == null) {
      builder.append("Recurring interval \n");
    }
    if (getDepositDateValue() == null) {
      builder.append("Deposit date added \n");
    }
    if (getDepositDateValue().isBefore(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
        .getStartDate())
        || getDepositDateValue().isAfter(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
        .getEndDate())) {
      builder.append("Deposit date needs to be inside savings plan period: \n");
      builder.append(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
          .getStartToEndString());
    }

    warningAlert.setContentText(builder.toString());
    warningAlert.initOwner(this.getDialogPane().getScene().getWindow());
    warningAlert.showAndWait();
  }

  @FXML
  public void initialize() {
    recurringIntervalComboBox.getItems().addAll(RecurringType.labelValues());
    configureDepositAmountField();
    configureDepositDescriptionField();
    configureDepositDatePicker();
  }
}
