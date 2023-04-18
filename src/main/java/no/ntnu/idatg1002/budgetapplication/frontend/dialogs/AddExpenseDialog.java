package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

/**
 * Represents a custom dialog for adding an expense in the budget application. The dialog includes
 * fields for entering expense details, such as amount, description, recurring type, and expense
 * category.
 *
 * @author Erik Bjørnsen
 * @version 1.2
 */
public class AddExpenseDialog extends Dialog<Expense> {
  Expense newExpense;

  @FXML private TextField expenseAmountField;
  @FXML private TextField expenseDescriptionField;
  @FXML private ComboBox<ExpenseCategory> categoryComboBox;
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  /**
   * Constructs an AddExpenseDialog, setting up the user interface components and necessary input
   * validation.
   */
  public AddExpenseDialog() {
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

    ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
    this.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CLOSE);

    this.setResultConverter(
        dialogButton -> {
          if (dialogButton == submitButton) {
            if (assertAllFieldsValid()) {
              newExpense =
                  new Expense(
                      Integer.parseInt(getExpenseAmountField()),
                      getExpenseDescriptionField(),
                      getRecurringIntervalComboBox(),
                      getExpenseCategoryComboBox());
              return newExpense;
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Please fill out all fields in dialog");
              alert.showAndWait();
            }
          }
          return null;
        });

    // adds enums to combo boxes
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
    categoryComboBox.getItems().addAll(ExpenseCategory.values());

    // force the field to be numeric only
    expenseAmountField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                expenseAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });

    // force the field to not start with space
    expenseDescriptionField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                expenseDescriptionField.clear();
              }
            });
  }

  /**
   * Returns the text from the expense description input field.
   *
   * @return the description of the expense
   */
  private String getExpenseDescriptionField() {
    return expenseDescriptionField.getText();
  }

  /**
   * Returns the text from the expense amount input field.
   *
   * @return the amount of the expense
   */
  private String getExpenseAmountField() {
    return expenseAmountField.getText();
  }

  /**
   * Returns the selected value from the recurring interval combo box.
   *
   * @return the selected recurring type of the expense
   */
  private RecurringType getRecurringIntervalComboBox() {
    return recurringIntervalComboBox.getValue();
  }

  /**
   * Returns the selected value from the expense category combo box.
   *
   * @return the selected expense category of the expense
   */
  private ExpenseCategory getExpenseCategoryComboBox() {
    return categoryComboBox.getValue();
  }

  /**
   * Verifies that all input fields have valid values.
   *
   * @return true if all input fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!expenseDescriptionField.getText().isEmpty()
        && expenseAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && categoryComboBox.getValue() != null);
  }
}