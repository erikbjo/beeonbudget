package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.text.TabableView;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;


public class BudgetController implements Initializable{
  private Stage stage;
  private Scene scene;
  private Parent parent;
  private final PrimaryController primaryController;
  @FXML
  private TableView<Budget> budgetTableView;
  @FXML
  private TableColumn<Budget, ArrayList<Category>> categoryColumn;
  @FXML
  private TableColumn<Budget, ArrayList<Expense>> expenseColumn;
  @FXML
  private TableColumn<Budget, ArrayList<Income>> incomeColumn;
  @FXML
  private Button monthlyExpenseButton;
  @FXML
  private Button newExpenseButton;
  @FXML
  private Button newIncomeButton;
  @FXML
  private Button previousButtonInBudget;

  private Budget selectedBudget;

  private ObservableList<Budget> budgets;

  public BudgetController() throws IOException {
    this.primaryController = new PrimaryController();
    this.budgets = FXCollections.observableArrayList();
    this.budgetTableView = new TableView<>();
    this.monthlyExpenseButton = new Button();
    this.newExpenseButton = new Button();
    this.newIncomeButton = new Button();
    this.previousButtonInBudget = new Button();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle){
    expenseColumn = new TableColumn<>("Expenses");
    incomeColumn = new TableColumn<>("Income");
    categoryColumn = new TableColumn<>("Category");
    expenseColumn.setCellValueFactory(new PropertyValueFactory<Budget, ArrayList<Expense>>("expenses"));
    incomeColumn.setCellValueFactory(new PropertyValueFactory<Budget, ArrayList<Income>>("income"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<Budget, ArrayList<Category>>("category"));
    budgetTableView.getColumns().addAll(expenseColumn, incomeColumn, categoryColumn);
    budgetTableView.setItems(budgets);
  }
  @FXML
  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }
  public void switchToPrimaryFromBudgetMouseEvent(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onNewIncome() throws IOException {
    TextInputDialog incomeDialog = new TextInputDialog();
    incomeDialog.setTitle("New Income");
    incomeDialog.setHeaderText("Enter income");
    incomeDialog.setContentText("Amount:");

    ArrayList<Income> incomes = new ArrayList<>();
    Optional<String> incomeResult = incomeDialog.showAndWait();
    incomeResult.ifPresent(incomeAmount -> {
      Income income = new Income(Integer.parseInt(incomeAmount), "dsfdsfsd",
          RecurringType.NONRECURRING);
      Budget budget = new Budget("rehufgeruv");
      budget.addBudgetIncome(income);
      budgets.add(budget);
      budgetTableView.refresh();
    });
  }

  @FXML
  public void onNewExpense() throws IOException {

    /**
     Budget selectedBudget = budgetView.getSelectionModel().getSelectedItem();
     if (selectedBudget != null) {
     selectedBudget.addBudgetExpenses(
     new Expense(2, "Test expense", RecurringType.NONRECURRING,
     Category.HOUSING));
     budgetView.refresh();
     }
     */
  }

  @FXML
  void onMonthlyExpense() {

  }
}
