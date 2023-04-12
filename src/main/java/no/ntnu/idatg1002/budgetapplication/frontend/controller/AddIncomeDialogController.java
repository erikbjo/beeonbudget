package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddIncomeDialogController extends Dialog<Budget> {

  @FXML // fx:id="incomeAmountField"
  private TextField incomeAmountField; // Value injected by FXMLLoader
  @FXML // fx:id="incomeDescriptionField"
  private TextField incomeDescriptionField; // Value injected by FXMLLoader
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  @FXML private ComboBox<IncomeCategory> incomeCategoryComboBox;

  public AddIncomeDialogController() throws IOException {}

  public String getIncomeDescriptionField() {
    return incomeDescriptionField.getText();
  }

  public String getIncomeAmountField() {
    return incomeAmountField.getText();
  }

  public RecurringType getRecurringIntervalComboBox() {
    return recurringIntervalComboBox.getValue();
  }

  public IncomeCategory getIncomeCategoryComboBox() {
    return incomeCategoryComboBox.getValue();
  }

  boolean assertAllFieldsValid() {
    return (incomeDescriptionField.getText() != null
        && incomeAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && incomeCategoryComboBox.getValue() != null);
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert incomeAmountField != null
        : "fx:id=\"incomeAmountField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeDescriptionField != null
        : "fx:id=\"incomeDescriptionField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert recurringIntervalComboBox != null
        : "fx:id=\"recurringIntervalComboBox\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeCategoryComboBox != null
        : "fx:id=\"incomeCategoryComboBox\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";

    // adds enum to combo boxes
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
    incomeCategoryComboBox.getItems().addAll(IncomeCategory.values());

    // force the field to be numeric only
    incomeAmountField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                incomeAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });

    // force the field to not start with space
    incomeDescriptionField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                incomeDescriptionField.clear();
              }
            });
  }
}
