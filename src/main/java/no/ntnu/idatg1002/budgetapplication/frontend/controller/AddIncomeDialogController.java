package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddIncomeDialogController extends Dialog<Budget> {

  @FXML private Button addIncomeButton;

  @FXML private Button cancelButton;

  @FXML private TextField incomeAmount;

  @FXML private TextField incomeDescription;

  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  @FXML
  void onAddIncome(ActionEvent event) {}

  @FXML
  void switchToPreviousFromAddIncomeDialog(ActionEvent event) {}
}
