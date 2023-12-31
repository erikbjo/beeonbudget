package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ConfirmationAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddBudgetDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddExpenseDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddIncomeDialog;

/** Controller for the Budget GUI. */
public class BudgetController implements Initializable {
  private Stage stage;
  private Scene scene;

  @FXML private TableView<Expense> expenseTableView;
  @FXML private TableView<Income> incomeTableView;
  @FXML private TableColumn<Expense, ExpenseCategory> expenseCategoryColumn;
  @FXML private TableColumn<Expense, Integer> expenseColumn;
  @FXML private TableColumn<Expense, RecurringType> expenseRecurringColumn;
  @FXML private TableColumn<Income, IncomeCategory> incomeCategoryColumn;
  @FXML private TableColumn<Income, Integer> incomeColumn;
  @FXML private TableColumn<Income, RecurringType> incomeRecurringColumn;
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
  @FXML private Label budgetDateLabel;
  @FXML private Button nextBudgetButton;
  @FXML private Button previousBudgetButton;

  /**
   * Constructor for the BudgetController class.
   *
   * @throws IOException if an I/O error occurs.
   */
  public BudgetController() throws IOException {
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
    expenseCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("expenseCategoryString"));
    expenseCategoryColumn.setReorderable(false);
    expenseRecurringColumn.setCellValueFactory(new PropertyValueFactory<>("recurringType"));
    expenseRecurringColumn.setReorderable(false);

    incomeColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    incomeColumn.setReorderable(false);
    incomeCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("incomeCategoryString"));
    incomeCategoryColumn.setReorderable(false);
    incomeRecurringColumn.setCellValueFactory(new PropertyValueFactory<>("recurringType"));
    incomeRecurringColumn.setReorderable(false);

    incomeTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, oldIncomeSelected, newIncomeSelected) -> {
              if (newIncomeSelected != null) {
                expenseTableView.getSelectionModel().clearSelection();
              }
            });
    incomeTableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        getInformationFromSelectedItem();
      }
      incomeTableView.setOnKeyPressed(event1 -> {
        if (event1.getCode() == KeyCode.ENTER) {
          getInformationFromSelectedItem();
        }
      });
      incomeTableView.setOnKeyPressed(event1 -> {
        if (event1.getCode() == KeyCode.DELETE) {
          deleteRowFromTable(new ActionEvent());
        }
      });
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
    expenseTableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        getInformationFromSelectedItem();
      }
      expenseTableView.setOnKeyPressed(event1 -> {
        if (event1.getCode() == KeyCode.ENTER) {
          getInformationFromSelectedItem();
        }
      });
      expenseTableView.setOnKeyPressed(event1 -> {
        if (event1.getCode() == KeyCode.DELETE) {
          deleteRowFromTable(new ActionEvent());
        }
      });
    });

    userNameInBudget.setText(SessionAccount.getInstance().getAccount().getName());
    updateAllInBudgetView();
  }

  /**
   * Switches to the primary view from the budget view.
   *
   * @param event The event that triggered this method.
   * @throws IOException if primary.fxml file cannot be loaded.
   */
  @FXML
  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene newScene = ((Node) event.getSource()).getScene();
    newScene.getStylesheets().add(css);
    newScene.setRoot(root);
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
    Scene newScene = ((Node) event.getSource()).getScene();
    newScene.getStylesheets().add(css);
    newScene.setRoot(root);
  }

  /**
   * Handles the action event for the "Next Budget" button. This method attempts to select the next
   * budget from the current account's budgets. If successful, the budget view is updated to display
   * the selected budget's information. If there is no next budget, a warning alert is shown to
   * inform the user.
   */
  @FXML
  private void onNextBudget() {
    if (SessionAccount.getInstance().getAccount().getBudgets().size() > 1) {
      if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
        try {
          SessionAccount.getInstance().getAccount().selectNextBudget();
          updateAllInBudgetView();
        } catch (IndexOutOfBoundsException e) {
          WarningAlert warningAlert = new WarningAlert("There is no next budget");
          warningAlert.showAndWait();
        }
      } else {
        showNoBudgetErrorFromSelectNewBudget();
      }
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
    if (SessionAccount.getInstance().getAccount().getBudgets().size() > 1) {
      if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
        try {
          SessionAccount.getInstance().getAccount().selectPreviousBudget();
          updateAllInBudgetView();
        } catch (IndexOutOfBoundsException e) {
          WarningAlert warningAlert = new WarningAlert("There is no previous budget");
          warningAlert.showAndWait();
        }
      } else {
        showNoBudgetErrorFromSelectNewBudget();
      }
    }
  }

  /**
   * This method handles the event when the user clicks the "New Income" button. It displays a
   * dialog box where the user can enter the details of the new income. If the user confirms the new
   * income, it is added to the currently selected budget.
   *
   * @param event The ActionEvent that triggers the method call
   */
  @FXML
  public void onNewIncome(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      AddIncomeDialog dialog = new AddIncomeDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Income> result = dialog.showAndWait();
      result.ifPresent(
          income -> {
            SessionAccount.getInstance().getAccount().getSelectedBudget().addBudgetIncome(income);
            AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
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
    result.ifPresent(budget -> {
      SessionAccount.getInstance().getAccount().addBudget(budget);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
      updateBudgetMoneyText();
      updateBudgetInfoText();
    });
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
            AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
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
      WarningAlert warningAlert = new WarningAlert("Please Select a item to Delete");
      warningAlert.showAndWait();
    }
  }

  /**
   * Deletes the selected income row from the income table view after user confirmation. Updates
   * income pie chart, total pie chart, and budget money text.
   */
  @FXML
  private void deleteIncomeFromTable() {
    Income income =
        incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());

    ConfirmationAlert confirmationAlert =
        new ConfirmationAlert(
            "Delete item",
            "Are you sure you want to delete this income?\n" + income.getIncomeAsString());

    Optional<ButtonType> result = confirmationAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      SessionAccount.getInstance()
          .getAccount()
          .getSelectedBudget()
          .removeBudgetIncome(incomeTableView.getSelectionModel().getSelectedItem());
      incomeTableView.getItems().removeAll(incomeTableView.getSelectionModel().getSelectedItems());
      updateIncomePieChart();
      updateTotalPieChart();
      updateBudgetMoneyText();
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    } else {
      confirmationAlert.close();
    }
  }

  /**
   * Deletes the selected expense row from the expense table view after user confirmation. Updates
   * expense pie chart, total pie chart, and budget money text.
   */
  @FXML
  private void deleteExpenseFromTable() {
    Expense expense =
        expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());

    ConfirmationAlert confirmationAlert =
        new ConfirmationAlert(
            "Delete item",
            "Are you sure you want to delete this income?\n" + expense.getExpenseAsString());

    Optional<ButtonType> result = confirmationAlert.showAndWait();
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
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    } else {
      confirmationAlert.close();
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
      WarningAlert warningAlert = new WarningAlert("Please Select An Item To Show More Info.");
      warningAlert.showAndWait();
    }
  }

  /**
   * Displays an alert containing the information of the selected income item. The alert shows the
   * income's details as a string and has a cancel button to close the alert.
   */
  @FXML
  private void getInformationFromSelectedIncome() {
    Alert.AlertType type = AlertType.NONE;
    Alert alert = new Alert(type, "");
    Income income =
        incomeTableView.getItems().get(incomeTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Income Info");
    alert.setContentText(income.getIncomeAsString());
    alert.getButtonTypes().add(ButtonType.CANCEL);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.showAndWait();
  }

  /**
   * Displays an alert containing the information of the selected expense item. The alert shows the
   * expense's details as a string and has a cancel button to close the alert.
   */
  @FXML
  private void getInformationFromSelectedExpense() {
    Alert.AlertType type = AlertType.NONE;
    Alert alert = new Alert(type, "");
    Expense expense =
        expenseTableView.getItems().get(expenseTableView.getSelectionModel().getSelectedIndex());
    alert.setTitle("Expense Info");
    alert.setContentText(expense.getExpenseAsString());
    alert.initModality(Modality.APPLICATION_MODAL);
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
      Budget budget = SessionAccount.getInstance().getAccount().getSelectedBudget();

      ConfirmationAlert confirmationAlert =
          new ConfirmationAlert(
              "Delete budget",
              "Are you sure you want to delete this budget?\n" + budget.getBudgetName());

      Optional<ButtonType> result = confirmationAlert.showAndWait();
      if (result.isPresent() && (result.get() == ButtonType.OK)) {
        SessionAccount.getInstance()
            .getAccount()
            .removeBudget(SessionAccount.getInstance().getAccount().getSelectedBudget());
        if (!SessionAccount.getInstance().getAccount().getBudgets().isEmpty()) {
          SessionAccount.getInstance().getAccount().selectPreviousBudget();
        }
        updateAllInBudgetView();
        AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
      }
    } else {
      showNoBudgetErrorFromDeleteBudget();
    }
  }

  /** Displays an error alert when trying to add an expense or income without a budget. */
  @FXML
  private void showNoBudgetErrorFromNewMoneyAction() {
    WarningAlert warningAlert =
        new WarningAlert("Please create a budget before adding an expense or income");
    warningAlert.showAndWait();
  }

  /** Displays an error alert when trying to switch budgets without any existing budgets. */
  @FXML
  private void showNoBudgetErrorFromSelectNewBudget() {
    WarningAlert warningAlert =
        new WarningAlert("Please create a budget before trying to switch budget");
    warningAlert.showAndWait();
  }

  /** Displays an error alert when trying to delete a budget when no budgets are present. */
  @FXML
  private void showNoBudgetErrorFromDeleteBudget() {
    WarningAlert warningAlert = new WarningAlert("There is no budget to be deleted");
    warningAlert.showAndWait();
  }

  // UPDATE METHODS BELOW

  /** This method calls all the other update methods, so that the entire UI is updated. */
  private void updateAllInBudgetView() {
    updateItems();
    updateAllPieCharts();
    updateBudgetMoneyText();
    updateBudgetInfoText();
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

  /** Updates the pie chart for income. */
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

  /** Updates the pie chart for expense. */
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

  /** Updates the pie chart for income and expense. */
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

  /** Updates all the texts that is connected with current budget. */
  public void updateBudgetInfoText() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      budgetNameInBudget.setText(
          SessionAccount.getInstance().getAccount().getSelectedBudget().getBudgetName());
      userNameInBudget.setText(SessionAccount.getInstance().getAccount().getName());
    } else {
      setDefaultBudgetInfoText();
    }
  }

  /**
   * Updates all the texts that is connected with the expenses and incomes in the current budget.
   */
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
                  - SessionAccount.getInstance()
                      .getAccount()
                      .getSelectedBudget()
                      .getTotalExpense()));
      budgetDateLabel.setText(
          "Start Date: "
              + SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getStartDate()
                  .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
              + "  End Date: "
              + SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getEndDate()
                  .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
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
    budgetDateLabel.setText(noBudgetSelected);
  }

  /**
   * Sets all the texts that is connected to the incomes and expenses in the current budget, to show
   * that there is no budget currently selected.
   */
  private void setDefaultBudgetMoneyText() {
    String noBudgetSelected = "No budget selected";
    totalIncomeInBudget.setText(noBudgetSelected);
    totalExpenseInBudget.setText(noBudgetSelected);
    totalBudgetSum.setText(noBudgetSelected);
  }
}
