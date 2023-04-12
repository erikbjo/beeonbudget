package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.*;

public class AddExpenseDialogController extends Dialog<Budget> {

  @FXML // fx:id="expenseAmountField"
  private TextField expenseAmountField; // Value injected by FXMLLoader

  @FXML // fx:id="expenseDescriptionField"
  private TextField expenseDescriptionField; // Value injected by FXMLLoader

  @FXML private ComboBox<ExpenseCategory> categoryComboBox;
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  public AddExpenseDialogController() {}

  public String getExpenseDescriptionField() {
    return expenseDescriptionField.getText();
  }

  public String getExpenseAmountField() {
    return expenseAmountField.getText();
  }

  public RecurringType getRecurringIntervalComboBox() {
    return recurringIntervalComboBox.getValue();
  }

  public ExpenseCategory getExpenseCategoryComboBox() {
    return categoryComboBox.getValue();
  }

  boolean assertAllFieldsValid() {
    return (expenseDescriptionField.getText() != null
        && expenseAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && categoryComboBox.getValue() != null);
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert categoryComboBox != null
        : "fx:id=\"categoryComboBox\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert expenseAmountField != null
        : "fx:id=\"expenseAmount\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert expenseDescriptionField != null
        : "fx:id=\"expenseDescription\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert recurringIntervalComboBox != null
        : "fx:id=\"recurringIntervalComboBox\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";

    // adds enums to combo boxes
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
    categoryComboBox.getItems().addAll(ExpenseCategory.values());

    // force the field to be numeric only
    expenseAmountField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                expenseAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });

    // force the field to not start with space
    expenseDescriptionField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                expenseDescriptionField.clear();
              }
            });
  }
}
