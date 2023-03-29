package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class BudgetController implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent parent;
  private final PrimaryController primaryController;

  private final AddExpenseDialogController addExpenseDialogController;

  private final AddIncomeDialogController addIncomeDialogController;
  @FXML private TableView<Expense> expenseTableView;
  @FXML private TableView<Income> incomeTableView;
  @FXML private TableColumn<Expense, ExpenseCategory> expenseCategoryColumn;
  @FXML private TableColumn<Expense, Integer> expenseColumn;
  @FXML private TableColumn<Income, ExpenseCategory> incomeCategoryColumn;
  @FXML private TableColumn<Income, Integer> incomeColumn;
  @FXML private Button monthlyExpenseButton;
  @FXML private Button newExpenseButton;
  @FXML private Button newIncomeButton;
  @FXML private Button previousButtonInBudget;

  private ObservableList<String> budgetInformation;

  public BudgetController() throws IOException {
    this.primaryController = new PrimaryController();
    this.addExpenseDialogController = new AddExpenseDialogController();
    this.addIncomeDialogController = new AddIncomeDialogController();
    this.budgetInformation = FXCollections.observableArrayList("assffsa");
    this.incomeTableView = new TableView<>();
    this.expenseTableView = new TableView<>();
    this.monthlyExpenseButton = new Button();
    this.newExpenseButton = new Button();
    this.newIncomeButton = new Button();
    this.previousButtonInBudget = new Button();
    // else Database.getCurrentAccount().addBudget(new Budget("Test"));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // expenseColumn = new TableColumn<>("Expenses");
    // expenseCategoryColumn = new TableColumn<>("ExpenseCategory");
    // incomeColumn = new TableColumn<>("Income");
    // incomeCategoryColumn = new TableColumn<>("ExpenseCategory");
    expenseColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    expenseCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    incomeColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    incomeCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    expenseTableView.setItems(
        FXCollections.observableArrayList(
            Database.getCurrentAccount().getSelectedBudget().getExpenseList()));
    incomeTableView.setItems(
        FXCollections.observableArrayList(
            Database.getCurrentAccount().getSelectedBudget().getIncomeList()));
  }

  @FXML
  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  public void switchToPrimaryFromBudgetMouseEvent(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  @FXML
  public void onNewIncome(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onNewExpense(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void onMonthlyExpense() {}
}
