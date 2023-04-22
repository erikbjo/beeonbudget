package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
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
public class AddExpenseDialog extends Dialog<Expense> {

  @FXML public Button submitButton;
  @FXML private TextField expenseAmountField;
  @FXML private TextField expenseDescriptionField;
  @FXML private ComboBox<String> categoryComboBox;
  @FXML private ComboBox<String> recurringIntervalComboBox;
  @FXML private DatePicker expenseDatePicker;
  @FXML private Button cancelButton;
  /**
   * Constructs an AddExpenseDialog, setting up the user interface components and necessary input
   * validation.
   */
  public AddExpenseDialog() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
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
    this.setTitle("Add Expense");
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
    Expense newExpense;
    if (assertAllFieldsValid()) {
      try {
        newExpense =
            new Expense(
                Integer.parseInt(getExpenseAmountFieldText()),
                getExpenseDescriptionFieldText(),
                getRecurringIntervalComboBoxValue(),
                getExpenseCategoryComboBoxValue(),
                getExpenseDateValue());
        this.setResult(newExpense);
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
  private void configureExpenseAmountField() {
    expenseAmountField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                expenseAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });
  }

  /**
   * Configures the expenseDescriptionField to prevent input from starting with a space. If a space
   * is entered at the beginning of the input, it is removed.
   */
  private void configureExpenseDescriptionField() {
    expenseDescriptionField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                expenseDescriptionField.clear();
              }
            });
  }

  private void configureExpenseDatePicker() {
    expenseDatePicker
        .focusedProperty()
        .addListener(
            (observableValue, oldPropertyValue, newPropertyValue) -> {
              if (Boolean.TRUE.equals(newPropertyValue)) {
                expenseDatePicker.show();
              } else {
                expenseDatePicker.hide();
              }
            });
  }

  /**
   * Returns the text from the expense description input field.
   *
   * @return the description of the expense
   */
  private String getExpenseDescriptionFieldText() {
    return expenseDescriptionField.getText();
  }

  /**
   * Returns the text from the expense amount input field.
   *
   * @return the amount of the expense
   */
  private String getExpenseAmountFieldText() {
    return expenseAmountField.getText();
  }

  /**
   * Returns the selected value from the recurring interval combo box.
   *
   * @return the selected recurring type of the expense
   */
  private RecurringType getRecurringIntervalComboBoxValue() {
    return RecurringType.valueOfLabel(recurringIntervalComboBox.getValue());
  }

  /**
   * Returns the selected value from the expense category combo box.
   *
   * @return the selected expense category of the expense
   */
  private ExpenseCategory getExpenseCategoryComboBoxValue() {
    return ExpenseCategory.valueOfLabel(categoryComboBox.getValue());
  }

  private LocalDate getExpenseDateValue() {
    LocalDate date = expenseDatePicker.getValue();
    return date;
  }

  /**
   * Verifies that all input fields have valid values.
   *
   * @return true if all input fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!getExpenseAmountFieldText().isEmpty()
        && !getExpenseAmountFieldText().isBlank()
        && !getExpenseDescriptionFieldText().isEmpty()
        && !getExpenseDescriptionFieldText().isBlank()
        && getRecurringIntervalComboBoxValue() != null
        && getExpenseCategoryComboBoxValue() != null
        && getExpenseDateValue() != null
        && !getExpenseDateValue().isBefore(SessionAccount.getInstance().getAccount().getSelectedBudget()
        .getStartDate())
        && !getExpenseDateValue().isAfter(SessionAccount.getInstance().getAccount().getSelectedBudget()
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

    if (getExpenseAmountFieldText().isEmpty() || getExpenseAmountFieldText().isBlank()) {
      builder.append("Amount \n");
    }
    if (getExpenseDescriptionFieldText().isEmpty() || getExpenseDescriptionFieldText().isBlank()) {
      builder.append("Description \n");
    }
    if (getRecurringIntervalComboBoxValue() == null) {
      builder.append("Recurring interval \n");
    }
    if (getExpenseCategoryComboBoxValue() == null) {
      builder.append("Category \n");
    }
    if (getExpenseDateValue() == null) {
      builder.append("Expense date added \n");
    }
    if (getExpenseDateValue().isBefore(SessionAccount.getInstance().getAccount().getSelectedBudget()
        .getStartDate())
    || getExpenseDateValue().isAfter(SessionAccount.getInstance().getAccount().getSelectedBudget()
        .getEndDate())) {
      builder.append("Expense date need to be inside budget period: \n");
      builder.append(SessionAccount.getInstance().getAccount().getSelectedBudget()
          .getStartToEndString());
    }

    warningAlert.setContentText(builder.toString());
    warningAlert.initOwner(this.getDialogPane().getScene().getWindow());
    warningAlert.showAndWait();
  }

  @FXML
  public void initialize() {
    recurringIntervalComboBox.getItems().addAll(RecurringType.labelValues());
    categoryComboBox.getItems().addAll(ExpenseCategory.stringValues());
    configureExpenseAmountField();
    configureExpenseDescriptionField();
    configureExpenseDatePicker();
  }
}
