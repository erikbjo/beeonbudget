package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class BudgetController implements Initializable {
  private Stage stage;
  private Scene scene;
  private ObservableList<String> budgetInformation;

  // Keys for hashmap
  private final String amountKey = "amount";
  private final String descriptionKey = "description";
  private final String recurringTypeKey = "recurringType";
  private final String categoryKey = "category";

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

  public BudgetController() throws IOException {
    this.budgetInformation = FXCollections.observableArrayList("assffsa");
    this.incomeTableView = new TableView<>();
    this.expenseTableView = new TableView<>();
    this.monthlyExpenseButton = new Button();
    this.newExpenseButton = new Button();
    this.newIncomeButton = new Button();
    this.previousButtonInBudget = new Button();
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    Parent root = loader.load();
    AddIncomeDialogController controller = loader.getController();

    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();

    // create a new dialog
    Dialog<HashMap> dialog = new Dialog<>();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
    dialog.setTitle("Add Income");

    // set the dialog's content to the loaded FXML file
    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(root);
    dialogPane.getStylesheets().add(css);
    dialog.setDialogPane(dialogPane);

    // add a "submit" button to the dialog
    ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

    // set the result converter to return the budget
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == submitButton) {
            if (controller.assertAllFieldsValid()) {
              HashMap values = new HashMap();
              values.put(amountKey, controller.getIncomeAmountField());
              values.put(descriptionKey, controller.getIncomeDescriptionField());
              values.put(recurringTypeKey, controller.getRecurringIntervalComboBox());
              values.put(categoryKey, controller.getIncomeCategoryComboBox());
              return values;
            } else System.out.println("Please fill out all fields in dialog");
          }
          return null;
        });

    // show the dialog and wait for a response
    Optional<HashMap> result = dialog.showAndWait();
    if (result.isPresent()) {
      Income newIncome =
          new Income(
              Integer.parseInt(result.get().get(amountKey).toString()),
              result.get().get(descriptionKey).toString(),
              (RecurringType) result.get().get(recurringTypeKey),
              (IncomeCategory) result.get().get(categoryKey));

      Database.getCurrentAccount().getSelectedBudget().addBudgetIncome(newIncome);
      updateItems();
    }
  }

  @FXML
  public void onNewExpense(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    Parent root = loader.load();
    AddExpenseDialogController controller = loader.getController();

    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();

    // create a new dialog
    Dialog<HashMap> dialog = new Dialog<>();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
    dialog.setTitle("Add Expense");

    // set the dialog's content to the loaded FXML file
    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(root);
    dialogPane.getStylesheets().add(css);
    dialog.setDialogPane(dialogPane);

    // add a "submit" button to the dialog
    ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == submitButton) {
            if (controller.assertAllFieldsValid()) {
              HashMap values = new HashMap();
              values.put(amountKey, controller.getExpenseAmountField());
              values.put(descriptionKey, controller.getExpenseDescriptionField());
              values.put(recurringTypeKey, controller.getRecurringIntervalComboBox());
              values.put(categoryKey, controller.getExpenseCategoryComboBox());
              return values;
            } else System.out.println("Please fill out all fields in dialog");
          }
          return null;
        });

    // show the dialog and wait for a response
    Optional<HashMap> result = dialog.showAndWait();
    if (result.isPresent()) {
      Expense newExpense =
          new Expense(
              Integer.parseInt(result.get().get(amountKey).toString()),
              result.get().get(descriptionKey).toString(),
              (RecurringType) result.get().get(recurringTypeKey),
              (ExpenseCategory) result.get().get(categoryKey));

      Database.getCurrentAccount().getSelectedBudget().addBudgetExpenses(newExpense);
      updateItems();
    }
  }

  private void updateItems() {
    expenseTableView.setItems(
        FXCollections.observableArrayList(
            Database.getCurrentAccount().getSelectedBudget().getExpenseList()));
    incomeTableView.setItems(
        FXCollections.observableArrayList(
            Database.getCurrentAccount().getSelectedBudget().getIncomeList()));
  }

  @FXML
  void onMonthlyExpense() {}
}
