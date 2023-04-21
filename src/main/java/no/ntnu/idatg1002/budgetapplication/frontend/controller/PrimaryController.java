package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddBudgetDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddExpenseDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddIncomeDialog;

/** This class is the controller for the primary GUI. */
public class PrimaryController implements Initializable {

  @FXML private Label menuPaneLabel1;
  @FXML private Label menuPaneLabel2;
  @FXML private Label usernameLabel;
  @FXML private Label budgetLabel;
  @FXML private PieChart budgetMenuChart;
  @FXML private JFXButton quitApplicationButton;
  @FXML private JFXButton logOutButton;
  @FXML private Button nextBudgetButton;
  @FXML private Button previousBudgetButton;
  @FXML private AnchorPane contentPane;
  @FXML private Stage stage;
  @FXML private Scene scene;

  /**
   * Constructs a new instance of the PrimaryController class.
   *
   * @throws IOException if the primary.fxml file cannot be loaded.
   */
  public PrimaryController() throws IOException {}

  /**
   * Switches the view to the budget view.
   *
   * @param event the event that triggered the method.
   * @throws IOException if the budget.fxml file cannot be loaded.
   */
  public void switchToBudget(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/budget.fxml"));
    Parent root = loader.load();
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/budget.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * Handles the action event for adding an income. If there's a selected budget, opens the
   * AddIncomeDialog and adds the income to the budget. If there's no budget, prompts the user to
   * create one.
   *
   * @param event the action event triggered by clicking the "Add Expense" button
   * @throws IOException if there's an error during dialog creation or processing
   */
  @FXML
  public void onAddIncome(ActionEvent event) throws IOException {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      AddIncomeDialog dialog = new AddIncomeDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Income> result = dialog.showAndWait();
      result.ifPresent(
          income -> {
            SessionAccount.getInstance().getAccount().getSelectedBudget().addBudgetIncome(income);
            updatePrimaryView();
          });
    } else {
      showNoBudgetCreateBudgetConfirmation(event);
    }
  }

  /**
   * Handles the action event for adding an expense. If there's a selected budget, opens the
   * AddExpenseDialog and adds the expense to the budget. If there's no budget, prompts the user to
   * create one.
   *
   * @param event the action event triggered by clicking the "Add Expense" button
   * @throws IOException if there's an error during dialog creation or processing
   */
  @FXML
  public void onAddExpense(ActionEvent event) throws IOException {
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
            updatePrimaryView();
          });
    } else {
      showNoBudgetCreateBudgetConfirmation(event);
    }
  }

  /**
   * Switches the view to the savings plan view.
   *
   * @param event the event that triggered the method.
   * @throws IOException if the savingsPlan.fxml file cannot be loaded.
   */
  public void switchToSavingPlan(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/fxmlfiles/savingsPlan.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setOnCloseRequest(windowEvent -> AccountDAO.getInstance().close());
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/savingsPlan.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  private void updatePrimaryView() {
    updatePrimaryLabel();
    updateTotalPieChart();
  }

  /** Updates the dynamic labels of the view. */
  public void updatePrimaryLabel() {
    if (SessionAccount.getInstance().getAccount() == null) {
      System.out.println("Account is null");
    }
    try {
      usernameLabel.setText(SessionAccount.getInstance().getAccount().getName());
    } catch (Exception ignored) {
    }
    try {
      budgetLabel.setText(
          String.format(
              "Budget: %s",
              SessionAccount.getInstance().getAccount().getSelectedBudget().getBudgetName()));
      menuPaneLabel1.setText(
          String.format(
              "Remaining: %dkr",
              SessionAccount.getInstance().getAccount().getSelectedBudget().getNetBalance()));
      menuPaneLabel2.setText(
          String.format(
              "Budget spent: %dkr",
              SessionAccount.getInstance().getAccount().getSelectedBudget().getTotalExpense()));
    } catch (Exception ignored) {
    }
  }

  /**
   * Displays a confirmation dialog prompting the user to create a budget if none exists. If the
   * user confirms, opens the AddBudgetDialog and creates the budget.
   *
   * @param event the action event that triggered the dialog
   * @throws IOException if there's an error during dialog creation or processing
   */
  private void showNoBudgetCreateBudgetConfirmation(ActionEvent event) throws IOException {
    System.out.println("Source: " + event.getSource());
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type, "Delete Item");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setTitle("No Budget");
    alert.setContentText(
        "You need to have a budget before adding an expense or income. "
            + "\nYou have no budgets currently, do you want to make one?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      showCreateBudgetDialogFromNoBudget(event);
    } else {
      alert.close();
    }
  }

  /**
   * Initializes the controller by updating the dynamic labels.
   *
   * @param url the URL of the FXML file that is being loaded.
   * @param resourceBundle the resource bundle of the FXML file that is being loaded.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updatePrimaryView();
  }

  /**
   * Updates the total pie chart data based on the selected budget. If there's a selected budget,
   * displays the total income and outcome. If there's no budget, clears the chart data.
   */
  private void updateTotalPieChart() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      budgetMenuChart.setData(
          FXCollections.observableArrayList(
              SessionAccount.getInstance()
                  .getAccount()
                  .getSelectedBudget()
                  .getTotalIncomeAndOutCome()));
    } else {
      budgetMenuChart.getData().clear();
    }
  }

  /**
   * Handles the action event for quitting the application. Displays a confirmation dialog and exits
   * the application if the user confirms.
   *
   * @param event the action event triggered by clicking the "Quit" button
   */
  public void quitApplication(ActionEvent event) {
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type, "");
    alert.setTitle("Quit");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.getDialogPane();
    alert.setContentText("Are you sure you want to quit? ");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Platform.exit();
      AccountDAO.getInstance().close();
    } else {
      alert.close();
    }
  }

  @FXML
  private void logOutUser(ActionEvent  actionEvent) throws IOException {
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type, "");
    alert.setTitle("Log Out");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.getDialogPane();
    alert.setContentText("Are you sure you want to LogOut? ");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      SessionAccount.getInstance().clearAccount();
      goBackToLogin(actionEvent);
    } else {
      alert.close();
    }
  }

  /**
   * Opens the AddBudgetDialog to create a new budget. Adds the created budget to the session
   * account, updates the primary view, and invokes the appropriate add expense or add income method
   * based on the event source.
   *
   * @param event the action event that triggered the dialog
   * @throws IOException if there's an error during dialog creation or processing
   */
  private void showCreateBudgetDialogFromNoBudget(ActionEvent event) throws IOException {
    AddBudgetDialog dialog = new AddBudgetDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Budget> result = dialog.showAndWait();
    result.ifPresent(budget -> SessionAccount.getInstance().getAccount().addBudget(budget));

    updatePrimaryView();

    if (Objects.equals(((Node) event.getSource()).getId(), "addExpenseButton")) {
      onAddExpense(event);
    } else if (Objects.equals(((Node) event.getSource()).getId(), "addIncomeButton")) {
      onAddIncome(event);
    }
  }
  @FXML
  private void onPreviousBudget() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      try {
        SessionAccount.getInstance().getAccount().selectPreviousBudget();
        updatePrimaryView();
      } catch (IndexOutOfBoundsException e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText("There is no previous budget");
        alert.showAndWait();
      }
    } else {
      showNoBudgetErrorFromSelectNewBudget();
    }
  }
  @FXML
  private void onNextBudget() {
    if (SessionAccount.getInstance().getAccount().getCurrentBudgetIndex() != null) {
      try {
        SessionAccount.getInstance().getAccount().selectNextBudget();
        updatePrimaryView();
      } catch (IndexOutOfBoundsException e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText("There is no next budget");
        alert.showAndWait();
      }
    } else {
      showNoBudgetErrorFromSelectNewBudget();
    }
  }
  private void showNoBudgetErrorFromSelectNewBudget() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Please create a budget before trying to switch budget");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  @FXML
  void goBackToLogin(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }
}
