package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddExpenseDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddIncomeDialog;

/**
 * This class is the controller for the primary GUI.
 */
public class PrimaryController implements Initializable {

  @FXML private Label menuPaneLabel1;
  @FXML private Label menuPaneLabel2;
  @FXML private Label usernameLabel;
  @FXML private Label budgetLabel;
  private Stage stage;
  private Scene scene;
  private Scene previousScene;

  /**
   Constructs a new instance of the PrimaryController class.

   @throws IOException if the primary.fxml file cannot be loaded.
   */
  public PrimaryController() throws IOException {}

  /**
   Switches the view to the budget view.

   @param event the event that triggered the method.
   @throws IOException if the budget.fxml file cannot be loaded.
   */
  public void switchToBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/budget.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String css = this.getClass().getResource("/cssfiles/budget.css").toExternalForm();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  /**
   Shows a dialog for adding a new income and updates the income table.

   @param event the event that triggered the method.
   @throws IOException if the AddIncomeDialog.fxml file cannot be loaded.
   */
  @FXML
  public void onAddIncome(ActionEvent event) throws IOException {
    AddIncomeDialog dialog = new AddIncomeDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Income> result = dialog.showAndWait();
    result.ifPresent(
        income -> Database.getCurrentAccount().getSelectedBudget().addBudgetIncome(income));
  }

  /**
   Shows a dialog for adding a new expense and updates the expense table.

   @param event the event that triggered the method.
   */
  @FXML
  public void onAddExpense(Event event) {
    AddExpenseDialog dialog = new AddExpenseDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<Expense> result = dialog.showAndWait();
    result.ifPresent(
        expense -> Database.getCurrentAccount().getSelectedBudget().addBudgetExpenses(expense));
  }

  /**
   Switches the view to the savings plan view.

   @param event the event that triggered the method.
   @throws IOException if the savingsPlan.fxml file cannot be loaded.
   */
  public void switchToSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/savingsPlan.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/savingsPlan.css"))
            .toExternalForm();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  /**
   Updates the dynamic labels of the view.
   */
  public void updateDynamicLabels() {
    budgetLabel.setText(
        String.format(
            "Budget: %s", Database.getCurrentAccount().getSelectedBudget().getBudgetName()));
    usernameLabel.setText(Database.getCurrentAccount().getName());
    menuPaneLabel1.setText(
        String.format(
            "Remaining: %dkr", Database.getCurrentAccount().getSelectedBudget().getNetBalance()));
    menuPaneLabel2.setText(
        String.format(
            "Budget spent: %dkr",
            Database.getCurrentAccount().getSelectedBudget().getTotalExpense()));
  }

  /**
   Initializes the controller by updating the dynamic labels.

   @param url the URL of the FXML file that is being loaded.
   @param resourceBundle the resource bundle of the FXML file that is being loaded.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateDynamicLabels();
  }
}
