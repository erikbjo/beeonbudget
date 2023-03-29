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
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddExpenseDialogController extends Dialog<Budget> {

  private SavingsPlanController savingsPlanController;
  private Stage stage;
  private Scene scene;

  @FXML private Button cancelExpenseDialogButton;

  @FXML // fx:id="submitExpenseDialogButton"
  private Button submitExpenseDialogButton; // Value injected by FXMLLoader
  @FXML // fx:id="expenseAmountField"
  private TextField expenseAmountField; // Value injected by FXMLLoader

  @FXML // fx:id="expenseDescriptionField"
  private TextField expenseDescriptionField; // Value injected by FXMLLoader

  @FXML private ComboBox<ExpenseCategory> categoryComboBox;
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  public AddExpenseDialogController() throws IOException {
    super();

    savingsPlanController = new SavingsPlanController();

    recurringIntervalComboBox = new ComboBox<>();
    categoryComboBox = new ComboBox<>();
  }

  private boolean assertAllFieldsValid() {
    return (expenseDescriptionField.getText() != null
        && expenseAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && categoryComboBox.getValue() != null);
  }

  @FXML
  void onSubmitExpenseDialog(ActionEvent event) {
    if (assertAllFieldsValid()) {
      Expense newExpense =
          new Expense(
              Integer.parseInt(expenseAmountField.getText()),
              expenseDescriptionField.getText(),
              recurringIntervalComboBox.getValue(),
              categoryComboBox.getValue());

      // for testing
      System.out.println("Created new object: " + newExpense);
    }
  }

  @FXML
  void switchToPreviousFromAddExpenseDialog(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().add(css);
    stage.show();
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert cancelExpenseDialogButton != null
        : "fx:id=\"cancelExpenseDialogButton\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert categoryComboBox != null
        : "fx:id=\"categoryComboBox\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert expenseAmountField != null
        : "fx:id=\"expenseAmount\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert expenseDescriptionField != null
        : "fx:id=\"expenseDescription\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert recurringIntervalComboBox != null
        : "fx:id=\"recurringIntervalComboBox\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";
    assert submitExpenseDialogButton != null
        : "fx:id=\"submitExpenseDialogButton\" was not injected: check your FXML file 'addExpenseDialog.fxml'.";

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
