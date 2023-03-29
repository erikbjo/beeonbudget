package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;import no.ntnu.idatg1002.budgetapplication.backend.Budget;

public class AddExpenseDialogController extends Dialog<Budget> {

  @FXML
  void onAddExpense(ActionEvent event) {}

  @FXML
  void switchToPreviousFromAddExpenseDialog(ActionEvent event) {}
}
