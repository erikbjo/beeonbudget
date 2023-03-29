package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddExpenseDialogController extends Dialog<Budget> {

  @FXML private ComboBox<Category> categoryComboBox;

  @FXML private TextField expenseAmount;

  @FXML private TextField expenseDescription;

  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  @FXML
  void onAddExpense(ActionEvent event) {}

  @FXML
  void switchToPreviousFromAddExpenseDialog(ActionEvent event) {}
}
