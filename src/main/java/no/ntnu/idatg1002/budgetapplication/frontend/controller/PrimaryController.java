package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class PrimaryController extends Dialog<Budget> {

  private Stage stage;
  private Scene scene;
  private GridPane expenseGrid;
  private TextField expenseField;
  private TextField descriptionField;
  private TextField monthsField;
  private ButtonType addButtonType;

  private ComboBox<RecurringType> recurringTypeComboBox;
  private ComboBox<Category> categoryComboBox;

  public PrimaryController() throws IOException {
    super();
    expenseField = new TextField();
    // expenseField.textProperty().addListener((observableValue, oldValue, newValue) -> {});

    descriptionField = new TextField();
    // descriptionField.textProperty().addListener((observableValue, oldValue, newValue) -> {});

    monthsField = new TextField();
    // monthsField.textProperty().addListener((observableValue, oldValue, newValue) -> {});

    recurringTypeComboBox = new ComboBox<>();
    categoryComboBox = new ComboBox<>();

    recurringTypeComboBox.getItems().addAll(RecurringType.values());
    categoryComboBox.getItems().addAll(Category.values());

    expenseGrid = new GridPane();
    expenseGrid.add(new Label("Expense Amount"), 0, 0);
    expenseGrid.add(new Label("Description"), 0, 2);
    expenseGrid.add(new Label("Recurring interval"), 0, 4);
    expenseGrid.add(new Label("Category"), 0, 6);

    expenseGrid.add(expenseField, 0, 1);
    expenseGrid.add(descriptionField, 0, 3);
    expenseGrid.add(recurringTypeComboBox, 0, 5);
    expenseGrid.add(categoryComboBox, 0, 7);
    expenseGrid.setHgap(10);
    expenseGrid.setVgap(10);
    /**
     * getDialogPane().setContent(expenseGrid); Node addButton =
     * getDialogPane().lookupButton(addButtonType); addButton.setDisable(true);
     * expenseField.textProperty().addListener((observable, oldValue, newValue) -> {
     * addButton.setDisable(newValue.trim().isEmpty()); });
     */
    Stage stage = (Stage) getDialogPane().getScene().getWindow();
    stage
        .getIcons()
        .add(new Image(getClass().getResource("/images/simpleLogoBoY.png").openStream()));

    /**
     * getDialogPane().getIcons().add(new Image(
     * getClass().getResource("/images/simpleLogoBoY.png").openStream()));
     */
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
    String css = this.getClass().getResource("/cssfiles/budget.css").toExternalForm();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddIncome(ActionEvent event) throws IOException {
    /*
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
    incomeGrid.add(new Label("Recurring interval"), 0, 4);

    incomeGrid.add(incomeField, 0, 1);
    incomeGrid.add(descriptionField, 0, 3);
    incomeGrid.add(recurringTypeComboBox, 0, 5);
    incomeGrid.setHgap(10);
    incomeGrid.setVgap(10);

    incomeDialog.getDialogPane().setContent(incomeGrid);
    Node addButton = incomeDialog.getDialogPane().lookupButton(addButtonType);
    addButton.setDisable(true);
    incomeField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              addButton.setDisable(newValue.trim().isEmpty());
            });

    Optional<Budget> incomeResult = incomeDialog.showAndWait();
    if (incomeResult.isPresent()) {
      Budget income = incomeResult.get();
      // Do something with the income
    }
    */

    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddExpense(ActionEvent event) throws IOException {
    // Optional<Budget> expenseResult = showAndWait();
    // if (expenseResult.isPresent()) {
    //  Budget expense = expenseResult.get();
    //  // Do something with the input
    // }

    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/savingsPlan.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
