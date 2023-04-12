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

public class PrimaryController extends Dialog<Budget> implements Initializable {

  // Keys for hashmap
  private final String amountKey = "amount";
  private final String descriptionKey = "description";
  private final String recurringTypeKey = "recurringType";
  private final String categoryKey = "category";
  @FXML private Label menuPaneLabel1;
  @FXML private Label menuPaneLabel2;
  @FXML private Label usernameLabel;
  @FXML private Label budgetLabel;
  private Stage stage;
  private Scene scene;
  private Scene previousScene;

  public PrimaryController() throws IOException {}

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

  @FXML
  public void onAddIncome(ActionEvent event) throws IOException {
    AddIncomeDialog dialog = new AddIncomeDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    // show the dialog and wait for a response
    Optional<HashMap> result = dialog.showAndWait();
    if (result.isPresent() && !result.get().isEmpty()) {
      Income newIncome =
          new Income(
              Integer.parseInt(result.get().get(amountKey).toString()),
              result.get().get(descriptionKey).toString(),
              (RecurringType) result.get().get(recurringTypeKey),
              (IncomeCategory) result.get().get(categoryKey));

      Database.getCurrentAccount().getSelectedBudget().addBudgetIncome(newIncome);
    }
  }

  @FXML
  public void onAddExpense(Event event) {
    AddExpenseDialog dialog = new AddExpenseDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<HashMap> result = dialog.showAndWait();
    if (result.isPresent() && !result.get().isEmpty()) {
      Expense newExpense =
          new Expense(
              Integer.parseInt(result.get().get(amountKey).toString()),
              result.get().get(descriptionKey).toString(),
              (RecurringType) result.get().get(recurringTypeKey),
              (ExpenseCategory) result.get().get(categoryKey));

      Database.getCurrentAccount().getSelectedBudget().addBudgetExpenses(newExpense);
    }
  }

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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateDynamicLabels();
  }
}
