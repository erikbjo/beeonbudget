package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddExpenseDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddIncomeDialog;

/** Controller for the Budget GUI */
public class BudgetController implements Initializable {
  private final ObservableList<String> budgetInformation;
  @FXML private final Button monthlyExpenseButton;
  private Stage stage;
  private Scene scene;

  @FXML
  private TableView<Expense> expenseTableView;
  @FXML
  private TableView<Income> incomeTableView;
  @FXML
  private TableColumn<Expense, ExpenseCategory> expenseCategoryColumn;
  @FXML
  private TableColumn<Expense, Integer> expenseColumn;
  @FXML
  private TableColumn<Income, IncomeCategory> incomeCategoryColumn;
  @FXML
  private TableColumn<Income, Integer> incomeColumn;
  @FXML
  private Button newExpenseButton;
  @FXML
  private Button newIncomeButton;
  @FXML
  private Button previousButtonInBudget;
  @FXML
  private PieChart incomeChart;
  @FXML
  private PieChart expenseChart;
  @FXML
  private Label totalExpenseInBudget;
  @FXML
  private Label totalIncomeInBudget;
  @FXML
  private Label userNameInBudget;
  @FXML
  private Label budgetNameInBudget;
  @FXML
  private PieChart totalChart;

  /**
   * Constructor for the BudgetController class.
   *
   * @throws IOException if an I/O error occurs.
   */
  public BudgetController() throws IOException {
    this.budgetInformation = FXCollections.observableArrayList("assffsa");
    this.incomeTableView = new TableView<>();
    this.expenseTableView = new TableView<>();
    this.monthlyExpenseButton = new Button();
    this.newExpenseButton = new Button();
    this.newIncomeButton = new Button();
    this.previousButtonInBudget = new Button();
  }

  /**
   * Initializes the controller class.
   *
   * @param url The location used to resolve relative paths for the root object.
   * @param resourceBundle The resources used to localize the root object.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    expenseColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    expenseColumn.setReorderable(false);
    expenseCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("expenseCategory"));
    expenseCategoryColumn.setReorderable(false);

    incomeColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    incomeColumn.setReorderable(false);
    incomeCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("incomeCategory"));
    incomeCategoryColumn.setReorderable(false);

    incomeTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, oldIncomeSelected, newIncomeSelected) -> {
              if (newIncomeSelected != null) {
                expenseTableView.getSelectionModel().clearSelection();
              }
            });

    expenseTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, oldExpenseSelected, newExpenseSelected) -> {
              if (newExpenseSelected != null) {
                incomeTableView.getSelectionModel().clearSelection();
              }
            });

    updateItems();
  }

  /**
   * Switches to the primary view from the budget view.
   *
   * @param event The event that triggered this method.
   * @throws IOException if an I/O error occurs.
   */
  @FXML
  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * This method switches the scene from the budget view to the primary view.
   *
   * @param event The MouseEvent that triggers the method call
   * @throws IOException if the primary.fxml file cannot be loaded
   */
  public void switchToPrimaryFromBudgetMouseEvent(MouseEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * This method handles the event when the user clicks the "New Income" button. It displays a
   * dialog box where the user can enter the details of the new income. If the user confirms the new
   * income, it is added to the currently selected budget.
   *
   * @param event The ActionEvent that triggers the method call
   * @throws IOException if the AddIncomeDialog.fxml file cannot be loaded
   */
  @FXML
  public void onNewIncome(ActionEvent event) throws IOException {
    AddIncomeDialog dialog = new AddIncomeDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Income> result = dialog.showAndWait();
    result.ifPresent(
        income -> Database.getCurrentAccount().getSelectedBudget().addBudgetIncome(income));
    updateItems();
  }

  /**
   * This method handles the event when the user clicks the "New Expense" button. It displays a
   * dialog box where the user can enter the details of the new expense. If the user confirms the
   * new expense, it is added to the currently selected budget.
   *
   * @param event The ActionEvent that triggers the method call
   * @throws IOException if the AddExpenseDialog.fxml file cannot be loaded
   */
  @FXML
  public void onNewExpense(ActionEvent event) throws IOException {
    AddExpenseDialog dialog = new AddExpenseDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Expense> result = dialog.showAndWait();
    result.ifPresent(
        expense -> Database.getCurrentAccount().getSelectedBudget().addBudgetExpenses(expense));
    updateItems();
  }

  /**
   * This method updates the items displayed in the expense and income table views. It retrieves the
   * expense and income lists from the currently selected budget and sets them as the new items for
   * the corresponding table views.
   */
  private void updateItems() {
    expenseTableView.getItems().clear();
    incomeTableView.getItems().clear();
    try {
      // update expenses
      expenseTableView.setItems(
          FXCollections.observableArrayList(
              Database.getCurrentAccount().getSelectedBudget().getExpenseList()));
      // update incomes
      incomeTableView.setItems(
          FXCollections.observableArrayList(
              Database.getCurrentAccount().getSelectedBudget().getIncomeList()));
    } catch (Exception ignored) {

    }
    pieChartUpdateExpense();
    pieChartUpdateIncome();
    pieChartUpdateTotal();
    updateBudgetView();
  }

  private void pieChartUpdateIncome() {
    if (Database.getCurrentAccount().getCurrentBudgetIndex() != null) {
      incomeChart.setData(
          FXCollections.observableArrayList(
              Database.getCurrentAccount().getSelectedBudget().getPieChartIncomeData()));
    } else {
      incomeChart.getData().clear();
    }
  }

  private void pieChartUpdateExpense() {
    if (Database.getCurrentAccount().getCurrentBudgetIndex() != null) {
      expenseChart.setData(
          FXCollections.observableArrayList(
              Database.getCurrentAccount().getSelectedBudget().getPieChartExpenseData()));
    } else {
      expenseChart.getData().clear();
    }
  }

  private void pieChartUpdateTotal() {
    if (Database.getCurrentAccount().getCurrentBudgetIndex() != null) {
      totalChart.setData(
          FXCollections.observableArrayList(
              Database.getCurrentAccount().getSelectedBudget().getTotalIncomeAndOutCome()));
    } else {
      totalChart.getData().clear();
    }
  }



  @FXML
  private void deleteRowFromTable(ActionEvent event) {
    if (incomeTableView
        .getSelectionModel()
        .isSelected(incomeTableView.getSelectionModel().getSelectedIndex())) {
      Alert.AlertType type = AlertType.CONFIRMATION;
      Alert alert = new Alert(type, "Delete Item");
      alert.initModality(Modality.APPLICATION_MODAL);
      Income income =
          incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());
      alert.setTitle("Are You Sure?");
      alert.setContentText("Are You Sure You Want To Delete This Income?" + "\n"
      +income.getIncomeAssString());
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        Database.getCurrentAccount()
            .getSelectedBudget()
            .removeBudgetIncome(incomeTableView.getSelectionModel().getSelectedItem());
        incomeTableView
            .getItems()
            .removeAll(incomeTableView.getSelectionModel().getSelectedItems());
        pieChartUpdateIncome();
        updateBudgetView();
      } else {
        alert.close();
      }
    } else if (expenseTableView
        .getSelectionModel()
        .isSelected(expenseTableView.getSelectionModel().getSelectedIndex())) {
      Alert.AlertType type = AlertType.CONFIRMATION;
      Alert alert = new Alert(type, "");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.getDialogPane();
      Expense expense =
          expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());
      alert.setTitle("Are You Sure?");
      alert.setContentText("Are You Sure You Want To Delete This Expense?" + "\n"
      +expense.getExpenseAssString());
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        Database.getCurrentAccount()
            .getSelectedBudget()
            .removeBudgetExpenses(expenseTableView.getSelectionModel().getSelectedItem());
        expenseTableView
            .getItems()
            .removeAll(expenseTableView.getSelectionModel().getSelectedItems());
        pieChartUpdateExpense();
        updateBudgetView();
      } else {
        alert.close();
      }
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please Select a item to Delete");
      alert.showAndWait();
    }
  }

  @FXML
  private void getInformationFromSelectedItem(ActionEvent event) throws IOException {
    if (incomeTableView
        .getSelectionModel()
        .isSelected(incomeTableView.getSelectionModel().getSelectedIndex())) {
      Alert.AlertType type = AlertType.NONE;
      Alert alert = new Alert(type, "");
      Income income =
          incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());
      alert.setTitle("Income Info");
      alert.setContentText(income.getIncomeAssString());
      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.showAndWait();
    } else if (expenseTableView
        .getSelectionModel()
        .isSelected(expenseTableView.getSelectionModel().getSelectedIndex())) {
      Alert.AlertType type = AlertType.NONE;
      Alert alert = new Alert(type, "");
      Expense expense =
          expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());
      alert.setTitle("Expense Info");
      alert.setContentText(expense.getExpenseAssString());
      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.showAndWait();
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please Select An Item To Show More Info.");
      alert.showAndWait();
    }
  }

  @FXML
  private void deleteBudget(ActionEvent event) {
    Database.getCurrentAccount().removeBudget(Database.getCurrentAccount().getSelectedBudget());
    updateItems();
  }

  public void updateBudgetView() {
    budgetNameInBudget.setText(Database.getCurrentAccount().getSelectedBudget().getBudgetName());
    totalIncomeInBudget.setText(
        String.valueOf(Database.getCurrentAccount().getSelectedBudget().getTotalIncome()));
    totalExpenseInBudget.setText(
        String.valueOf(Database.getCurrentAccount().getSelectedBudget().getTotalExpense()));
    userNameInBudget.setText(Database.getCurrentAccount().getName());
  }
}
