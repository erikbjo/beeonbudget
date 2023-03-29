package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class AddIncomeDialogController extends Dialog<Budget> {
  private Stage stage;
  private Scene scene;

  @FXML private Button cancelIncomeDialogButton;
  @FXML private Button submitIncomeButton;
  @FXML // fx:id="incomeAmountField"
  private TextField incomeAmountField; // Value injected by FXMLLoader
  @FXML // fx:id="incomeDescriptionField"
  private TextField incomeDescriptionField; // Value injected by FXMLLoader
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  @FXML private ComboBox<IncomeCategory> incomeCategoryComboBox;

  public AddIncomeDialogController() throws IOException {}

  private boolean assertAllFieldsValid() {
    return (incomeDescriptionField.getText() != null
        && incomeAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && incomeCategoryComboBox.getValue() != null);
  }

  @FXML
  void onSubmitIncomeDialog(ActionEvent event) throws IOException {
    if (assertAllFieldsValid()) {
      Income newIncome =
          new Income(
              Integer.parseInt(incomeAmountField.getText()),
              incomeDescriptionField.getText(),
              recurringIntervalComboBox.getValue(),
              incomeCategoryComboBox.getValue());

      Database.getCurrentAccount().getSelectedBudget().addBudgetIncome(newIncome);
      // for testing
      System.out.println("Created new object: " + newIncome);

      switchToPreviousFromAddIncomeDialog(event);
    }
  }

  @FXML
  void switchToPreviousFromAddIncomeDialog(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/budget.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().add(css);
    stage.show();
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert cancelIncomeDialogButton != null
        : "fx:id=\"cancelIncomeDialogButton\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeAmountField != null
        : "fx:id=\"incomeAmountField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeDescriptionField != null
        : "fx:id=\"incomeDescriptionField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert recurringIntervalComboBox != null
        : "fx:id=\"recurringIntervalComboBox\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeCategoryComboBox != null
        : "fx:id=\"incomeCategoryComboBox\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert submitIncomeButton != null
        : "fx:id=\"submitIncomeButton\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";

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
