package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    Parent root = loader.load();
    AddIncomeDialogController controller = loader.getController();

    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/dialog.css"))
            .toExternalForm();

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

    // set the result converter to return the values of the dialog
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
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Please fill out all fields in dialog");
              alert.showAndWait();
            }
          }
          return null;
        });

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
  public void onAddExpense(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    Parent root = loader.load();
    AddExpenseDialogController controller = loader.getController();

    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/dialog.css"))
            .toExternalForm();

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

    // set the result converter to return the values of the dialog
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
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Please fill out all fields in dialog");
              alert.showAndWait();
            }
          }
          return null;
        });

    // show the dialog and wait for a response
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
