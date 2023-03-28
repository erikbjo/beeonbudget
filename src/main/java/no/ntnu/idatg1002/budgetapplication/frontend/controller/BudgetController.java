package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.text.TabableView;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class BudgetController {
  private Stage stage;
  private Scene scene;
  private Parent parent;
  private final PrimaryController primaryController = new PrimaryController();

  @FXML private TableView<Budget> budgetView;
  @FXML private TableColumn<Budget, Expense> expenseColumn;
  @FXML private TableColumn<Budget, Income> incomeColumn;
  @FXML private TableColumn<Budget, String> categoryColumn;

  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void onNewIncome(ActionEvent event) throws IOException {
    // Budget selectedBudget = budgetView.getSelectionModel().getSelectedItem();
    // if (selectedBudget != null) {
    //  selectedBudget.addBudgetExpenses(
    //      new Expense(2, "dsafafsd", Category.HEALTHCARE, RecurringType.NONRECURRING));
    //  budgetView.refresh();
    // }
    primaryController.onAddExpense(event);
  }

  public void onNewExpense(ActionEvent event) throws IOException {
    Budget selectedBudget = budgetView.getSelectionModel().getSelectedItem();
    if (selectedBudget != null) {
      selectedBudget.addBudgetExpenses(
          new Expense(2, "Test expense", RecurringType.NONRECURRING, Category.HOUSING));
      budgetView.refresh();
    }
  }

  public void setBudgetView(Budget budget) {
    ObservableList<Budget> budgets = FXCollections.observableArrayList();
    budgets.add(budget);

    budgetView.setItems(budgets);

    expenseColumn.setCellValueFactory(new PropertyValueFactory<Budget, Expense>("expenses"));
    incomeColumn.setCellValueFactory(new PropertyValueFactory<Budget, Income>("income"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("category"));
  }
}
