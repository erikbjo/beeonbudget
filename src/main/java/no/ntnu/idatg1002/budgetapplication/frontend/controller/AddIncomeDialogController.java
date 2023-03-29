package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddIncomeDialogController extends Dialog<Budget> {

  private SavingsPlanController savingsPlanController;
  private Stage stage;
  private Scene scene;

  @FXML private Button cancelIncomeDialogButton;
  @FXML private Button submitIncomeButton;
  @FXML // fx:id="incomeAmountField"
  private TextField incomeAmountField; // Value injected by FXMLLoader
  @FXML // fx:id="incomeDescriptionField"
  private TextField incomeDescriptionField; // Value injected by FXMLLoader
  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  public AddIncomeDialogController() throws IOException {
    super();

    savingsPlanController = new SavingsPlanController();

    recurringIntervalComboBox = new ComboBox<>();
  }

  @FXML
  void onSubmitIncomeDialog(ActionEvent event) {
    Income newIncome =
        new Income(
            Integer.parseInt(incomeAmountField.getText()),
            incomeDescriptionField.getText(),
            recurringIntervalComboBox.getValue());

    // for testing
    System.out.println("Created new object: " + newIncome);
  }

  @FXML
  void switchToPreviousFromAddIncomeDialog(ActionEvent event) throws IOException {
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
    assert cancelIncomeDialogButton != null
        : "fx:id=\"cancelIncomeDialogButton\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeAmountField != null
        : "fx:id=\"incomeAmountField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert incomeDescriptionField != null
        : "fx:id=\"incomeDescriptionField\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert recurringIntervalComboBox != null
        : "fx:id=\"recurringIntervalComboBox\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";
    assert submitIncomeButton != null
        : "fx:id=\"submitIncomeButton\" was not injected: check your FXML file 'addIncomeDialog.fxml'.";

    // adds enum to combobox
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());

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
