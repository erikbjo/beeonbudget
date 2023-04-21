package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
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
   * Shows a dialog for adding a new income and updates the income table.
   *
   * @param event the event that triggered the method.
   * @throws IOException if the AddIncomeDialog.fxml file cannot be loaded.
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
      showNoBudgetError();
    }
  }

  /**
   * Shows a dialog for adding a new expense and updates the expense table.
   *
   * @param event the event that triggered the method.
   */
  @FXML
  public void onAddExpense(Event event) throws IOException {
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
      showNoBudgetError();
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
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/savingsPlan.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /** Updates the dynamic labels of the view. */
  public void updatePrimaryView() {
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
      updateTotalPieChart();
    } catch (Exception ignored) {
    }
  }

  private void showNoBudgetError() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(
        "Please create a budget in the budget view before adding an expense or income");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
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
    updateTotalPieChart();
  }
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

  public void quitApplication(ActionEvent event) {
    Alert.AlertType type = AlertType.CONFIRMATION;
    Alert alert = new Alert(type,"");
    alert.setTitle("Quit");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.getDialogPane();
    alert.setContentText("Are you sure you want to quit? ");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Platform.exit();
    } else {
      alert.close();
    }
  }
}


