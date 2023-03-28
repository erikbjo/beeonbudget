package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;

public class PrimaryController extends Dialog<Budget> {

  private Stage stage;
  private Scene scene;
  private GridPane expenseGrid;
  private TextField expenseField;
  private TextField descriptionField;
  private TextField monthsField;

  private ButtonType addButtonType;

  public PrimaryController() {
    super();
    expenseField = new TextField();
    expenseField.textProperty().addListener((observableValue, oldValue, newValue) -> {
    });
    descriptionField = new TextField();
    descriptionField.textProperty().addListener((observableValue, oldValue, newValue) -> {
    });
    monthsField = new TextField();
    monthsField.textProperty().addListener((observableValue, oldValue, newValue) -> {
    });
    expenseGrid = new GridPane();
    expenseGrid.add(new Label("Expense Amount"), 0, 0);
    expenseGrid.add(new Label("Description"), 0, 2);
    expenseGrid.add(new Label("Monthly Expense"), 0, 4);
    expenseGrid.add(new CheckBox(), 0, 5);
    expenseGrid.add(new Label("Category"), 0, 6);

    expenseGrid.add(expenseField, 0, 1);
    expenseGrid.add(descriptionField, 0, 3);
    expenseGrid.add(monthsField, 1, 5);
    expenseGrid.add(new ComboBox<Category>(), 0, 7);
    expenseGrid.setHgap(10);
    expenseGrid.setVgap(10);

    getDialogPane().setContent(expenseGrid);
    addButtonType = new ButtonType("Add");
    getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, addButtonType);
    setTitle("Add Expense");
    setHeaderText(null);
    setResizable(true);
  }

  public void switchToBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/budget.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddIncome(ActionEvent event) throws IOException {
    Dialog<Budget> incomeDialog = new Dialog<>();
    incomeDialog.setTitle("Add Income");
    incomeDialog.setHeaderText(null);

    ButtonType addButtonType = new ButtonType("Add");
    incomeDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    // Create the amount field
    TextField incomeField = new TextField();
    GridPane incomeGrid = new GridPane();
    incomeGrid.add(new Label("Income Amount"), 0, 0);
    incomeGrid.add(new Label("Description"), 0, 2);
    incomeGrid.add(new Label("Monthly Income"), 0, 4);
    incomeGrid.add(new CheckBox(), 0, 5);
    incomeGrid.add(new Label("Category"), 0, 6);

    incomeGrid.add(incomeField, 0, 1);
    incomeGrid.add(descriptionField, 0, 3);
    incomeGrid.add(monthsField, 1, 5);
    incomeGrid.add(new ComboBox<Category>(), 0, 7);
    incomeGrid.setHgap(10);
    incomeGrid.setVgap(10);

    // Enable/disable add button depending on whether amount field is empty
    incomeDialog.getDialogPane().setContent(incomeGrid);
    Node addButton = incomeDialog.getDialogPane().lookupButton(addButtonType);
    addButton.setDisable(true);
    incomeField.textProperty().addListener((observable, oldValue, newValue) -> {
      addButton.setDisable(newValue.trim().isEmpty());
    });

    /**
    // Create the grid pane and add the amount field
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(new Label("Amount:"), 0, 0);
    grid.add(incomeField, 1, 0);
     */

    Optional<Budget> incomeResult = incomeDialog.showAndWait();
    if (incomeResult.isPresent()) {
      Budget income = incomeResult.get();
      // Do something with the income
    }
  }


    public void onAddExpense (ActionEvent event) throws IOException {
      Optional<Budget> expenseResult = showAndWait();
      if (expenseResult.isPresent()) {
        Budget budget = expenseResult.get();
        // Do something with the input
      }
    }

    public void switchToSavingPlan(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/savingsPlan.fxml"));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
  }
