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
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddBudgetDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddExpenseDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddIncomeDialog;

/** Controller for the Budget GUI */
public class BudgetController implements Initializable {
  private final ObservableList<String> budgetInformation;
  private Stage stage;
  private Scene scene;

  @FXML private TableView<Expense> expenseTableView;
  @FXML private TableView<Income> incomeTableView;
  @FXML private TableColumn<Expense, ExpenseCategory> expenseCategoryColumn;
  @FXML private TableColumn<Expense, Integer> expenseColumn;
  @FXML private TableColumn<Income, IncomeCategory> incomeCategoryColumn;
  @FXML private TableColumn<Income, Integer> incomeColumn;
  @FXML private Button newExpenseButton;
  @FXML private Button newIncomeButton;
  @FXML private Button backButtonBudget;
  @FXML private PieChart incomeChart;
  @FXML private PieChart expenseChart;
  @FXML private Label totalExpenseInBudget;
  @FXML private Label totalIncomeInBudget;
  @FXML private Label userNameInBudget;
  @FXML private Label budgetNameInBudget;
  @FXML private Label totalBudgetSum;
  @FXML private PieChart totalChart;
  @FXML private Button nextBudgetButton;
  @FXML private Button previousBudgetButton;

  /**
   * Constructor for the BudgetController class.
   *
   * @throws IOException if an I/O error occurs.
   */
  public BudgetController() throws IOException {
    this.budgetInformation = FXCollections.observableArrayList("assffsa");
    this.incomeTableView = new TableView<>();
    this.expenseTableView = new TableView<>();
    this.newExpenseButton = new Button();
    this.newIncomeButton = new Button();
    this.backButtonBudget = new Button();
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

    userNameInBudget.setText(SessionAccount.getInstance().getAccount().getName());
    updateAllInBudgetView();
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
   * Handles the action event for the "Next Budget" button. This method attempts to select the next
   * budget from the current account's budgets. If successful, the budget view is updated to display
   * the selected budget's information. If there is no next budget, a warning alert is shown to
   * inform the user.
   */
  @FXML
  private void onNextBudget() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      try {
        SessionAccount.getInstance().getAccount().selectNextBudget();
        updateAllInBudgetView();
      } catch (IndexOutOfBoundsException e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText("There is no next budget");
        alert.showAndWait();
      }
    } else {
      showNoBudgetErrorFromSelectNewBudget();
    }
  }

  /**
   * Handles the action event for the "Previous Budget" button. This method attempts to select the
   * previous budget from the current account's budgets. If successful, the budget view is updated
   * to display the selected budget's information. If there is no previous budget, a warning alert
   * is shown to inform the user.
   */
  @FXML
  private void onPreviousBudget() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      try {
        SessionAccount.getInstance().getAccount().selectPreviousBudget();
        updateAllInBudgetView();
      } catch (IndexOutOfBoundsException e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText("There is no previous budget");
        alert.showAndWait();
      }
    } else {
      showNoBudgetErrorFromSelectNewBudget();
    }
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
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      AddIncomeDialog dialog = new AddIncomeDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Income> result = dialog.showAndWait();
      result.ifPresent(
          income -> {
            SessionAccount.getInstance().getAccount().getSelectedBudget().addBudgetIncome(income);
            updateItems();
            updateIncomePieChart();
            updateTotalPieChart();
            updateBudgetMoneyText();
          });
    } else {
      showNoBudgetErrorFromNewMoneyAction();
    }
  }

  /**
   * This method handles the event when the user clicks the "New Budget" button. It displays a
   * dialog box where the user can enter the name of the new budget. If the user confirms the new
   * budget is set as the active one, and the UI is updated.
   *
   * @param event The ActionEvent that triggers the method call
   * @throws IOException if the AddBudgetDialog.fxml file cannot be loaded
   */
  @FXML
  public void onNewBudget(ActionEvent event) throws IOException {
    AddBudgetDialog dialog = new AddBudgetDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Budget> result = dialog.showAndWait();
    result.ifPresent(budget -> SessionAccount.getInstance().getAccount().addBudget(budget));
    updateAllInBudgetView();
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
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      AddExpenseDialog dialog = new AddExpenseDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Expense> result = dialog.showAndWait();
      result.ifPresent(
          expense -> {
            SessionAccount.getInstance()
                .getAccount()
                .getSelectedBudget()
                .addBudgetExpenses(expense);
            updateItems();
            updateBudgetMoneyText();
            updateExpensePieChart();
            updateTotalPieChart();
          });
    } else {
      showNoBudgetErrorFromNewMoneyAction();
    }
  }

  /**
   * Handles the action event for the "Delete Row" button. This method determines if a row is
   * selected in either the income or expense table views. If a row is selected, the corresponding
   * deletion method is called. If no row is selected, a warning alert is shown to inform the user.
   *
   * @param event The action event object for the "Delete Row" button.
   */
  @FXML
  private void deleteRowFromTable(ActionEvent event) {
    if (incomeTableView
        .getSelectionModel()
        .isSelected(incomeTableView.getSelectionModel().getSelectedIndex())) {
      deleteIncomeFromTable();
    } else if (expenseTableView
        .getSelectionModel()
        .isSelected(expenseTableView.getSelectionModel().getSelectedIndex())) {
      deleteExpenseFromTable();
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please Select a item to Delete");
      alert.showAndWait();
    }
  }

  /**
   * Deletes the selected income row from the income table view after user confirmation. Updates
   * income pie chart, total pie chart, and budget money text.
   */
  private void deleteIncomeFromTable() {
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type, "Delete Item");
    alert.initModality(Modality.APPLICATION_MODAL);
    Income income =
        incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Are You Sure?");
    alert.setContentText(
        "Are You Sure You Want To Delete This Income?" + "\n" + income.getIncomeAssString());

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      SessionAccount.getInstance()
          .getAccount()
          .getSelectedBudget()
          .removeBudgetIncome(incomeTableView.getSelectionModel().getSelectedItem());
      incomeTableView.getItems().removeAll(incomeTableView.getSelectionModel().getSelectedItems());
      updateIncomePieChart();
      updateTotalPieChart();
      updateBudgetMoneyText();
    } else {
      alert.close();
    }
  }

  /**
   * Deletes the selected expense row from the expense table view after user confirmation. Updates
   * expense pie chart, total pie chart, and budget money text.
   */
  private void deleteExpenseFromTable() {
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type, "");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.getDialogPane();
    Expense expense =
        expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Are You Sure?");
    alert.setContentText(
        "Are You Sure You Want To Delete This Expense?" + "\n" + expense.getExpenseAssString());

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && (result.get() == ButtonType.OK)) {
      SessionAccount.getInstance()
          .getAccount()
          .getSelectedBudget()
          .removeBudgetExpenses(expenseTableView.getSelectionModel().getSelectedItem());
      expenseTableView
          .getItems()
          .removeAll(expenseTableView.getSelectionModel().getSelectedItems());
      updateExpensePieChart();
      updateTotalPieChart();
      updateBudgetMoneyText();
    } else {
      alert.close();
    }
  }

  /**
   * Handles the action event for fetching information from the selected item in either the income
   * or expense table views. Displays an alert with the respective item's information if an item is
   * selected, otherwise shows a warning alert to select an item.
   */
  @FXML
  private void getInformationFromSelectedItem() {
    if (incomeTableView
        .getSelectionModel()
        .isSelected(incomeTableView.getSelectionModel().getSelectedIndex())) {
      getInformationFromSelectedIncome();
    } else if (expenseTableView
        .getSelectionModel()
        .isSelected(expenseTableView.getSelectionModel().getSelectedIndex())) {
      getInformationFromSelectedExpense();
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please Select An Item To Show More Info.");
      alert.showAndWait();
    }
  }

  /**
   * Displays an alert containing the information of the selected income item. The alert shows the
   * income's details as a string and has a cancel button to close the alert.
   */
  private void getInformationFromSelectedIncome() {
    Alert.AlertType type = AlertType.NONE;
    Alert alert = new Alert(type, "");
    Income income =
        incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Income Info");
    alert.setContentText(income.getIncomeAssString());
    alert.getButtonTypes().add(ButtonType.CANCEL);
    alert.showAndWait();
  }

  /**
   * Displays an alert containing the information of the selected expense item. The alert shows the
   * expense's details as a string and has a cancel button to close the alert.
   */
  private void getInformationFromSelectedExpense() {
    Alert.AlertType type = AlertType.NONE;
    Alert alert = new Alert(type, "");
    Expense expense =
        expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Expense Info");
    alert.setContentText(expense.getExpenseAssString());
    alert.getButtonTypes().add(ButtonType.CANCEL);
    alert.showAndWait();
  }

  /**
   * Handles the action event for deleting a budget. Displays a confirmation alert asking the user
   * if they are sure they want to delete the selected budget. If the user confirms, the budget is
   * removed from the current account and the budget view is updated.
   *
   * @param event the action event triggered by the user
   */
  @FXML
  private void deleteBudget(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      Alert.AlertType type = AlertType.CONFIRMATION;
      Alert alert = new Alert(type, "");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.getDialogPane();
      Budget budget = SessionAccount.getInstance().getAccount().getSelectedBudget();
      alert.setTitle("Are You Sure?");
      alert.setContentText(
          "Are You Sure You Want To Delete This Budget?" + "\n" + budget.getBudgetName());
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && (result.get() == ButtonType.OK)) {
        SessionAccount.getInstance()
            .getAccount()
            .removeBudget(SessionAccount.getInstance().getAccount().getSelectedBudget());
        updateAllInBudgetView();
      }
    } else {
      showNoBudgetErrorFromDeleteBudget();
    }
  }

  private void showNoBudgetErrorFromNewMoneyAction() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Please create a budget before adding an expense or income");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  private void showNoBudgetErrorFromSelectNewBudget() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Please create a budget before trying to switch budget");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  private void showNoBudgetErrorFromDeleteBudget() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("There is no budget to be deleted");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  // UPDATE METHODS BELOW

  /** This method calls all the other update methods, so that all the whole UI is updated. */
  private void updateAllInBudgetView() {
    updateItems();
    updateAllPieCharts();
    updateBudgetInfoText();
    updateBudgetMoneyText();
  }

  /**
   * This method updates the items displayed in the expense and income table views. It retrieves the
   * expense and income lists from the currently selected budget and sets them as the new items for
   * the corresponding table views.
   */
  private void updateItems() {
    expenseTableView.getItems().clear();
    incomeTableView.getItems().clear();

    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      // update expenses
      expenseTableView.setItems(
          FXCollections.observableArrayList(
              SessionAccount.getInstance().getAccount().getSelectedBudget().getExpenseList()));
      // update incomes
      incomeTableView.setItems(
          FXCollections.observableArrayList(
              SessionAccount.getInstance().getAccount().getSelectedBudget().getIncomeList()));
    }
  }

  /** Updates all the pie charts. */
  private void updateAllPieCharts() {
    updateIncomePieChart();
    updateExpensePieChart();
    updateTotalPieChart();
  }

  /** Updates the pie chart for income */
  private void updateIncomePieChart() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      incomeChart.setData(
          FXCollections.observableArrayList(
              SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getPieChartIncomeData()));
    } else {
      incomeChart.getData().clear();
    }
  }

  /** Updates the pie chart for expense */
  private void updateExpensePieChart() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      expenseChart.setData(
          FXCollections.observableArrayList(
              SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getPieChartExpenseData()));
    } else {
      expenseChart.getData().clear();
    }
  }

  /** Updates the pie chart for income and expense */
  private void updateTotalPieChart() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      totalChart.setData(
          FXCollections.observableArrayList(
              SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getTotalIncomeAndOutCome()));
    } else {
      totalChart.getData().clear();
    }
  }

  /** Updates all the texts that is connected with current budget */
  public void updateBudgetInfoText() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      budgetNameInBudget.setText(
          SessionAccount.getInstance().getAccount().getSelectedBudget().getBudgetName());
      userNameInBudget.setText(SessionAccount.getInstance().getAccount().getName());
    } else {
      setDefaultBudgetInfoText();
    }
  }

  /** Updates all the texts that is connected with the expenses and incomes in the current budget */
  public void updateBudgetMoneyText() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      totalIncomeInBudget.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedBudget().getTotalIncome()));
      totalExpenseInBudget.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedBudget().getTotalExpense()));
      totalBudgetSum.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedBudget().getTotalIncome()
              - SessionAccount.getInstance().getAccount().getSelectedBudget().getTotalExpense()));
    } else {
      setDefaultBudgetMoneyText();
    }
  }

  /**
   * Sets all the texts that is connected to the current budget, to show that there is no budget
   * currently selected.
   */
  private void setDefaultBudgetInfoText() {
    String noBudgetSelected = "No budget selected";
    budgetNameInBudget.setText(noBudgetSelected);
  }

  /**
   * Sets all the texts that is connected to the incomes and expenses in the current budget, to show
   * that there is no budget currently selected.
   */
  private void setDefaultBudgetMoneyText() {
    String noBudgetSelected = "No budget selected";
    totalIncomeInBudget.setText(noBudgetSelected);
    totalExpenseInBudget.setText(noBudgetSelected);
  }
}
