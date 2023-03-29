package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddIncomeDialogController extends Dialog<Budget> {

  private SavingsPlanController savingsPlanController;
  private Stage stage;
  private Scene scene;
  @FXML private Button addIncomeButton;

  @FXML private Button cancelButton;

  @FXML private TextField incomeAmount;

  @FXML private TextField incomeDescription;

  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  public AddIncomeDialogController() throws IOException {
    super();

    savingsPlanController = new SavingsPlanController();

    recurringIntervalComboBox = new ComboBox<>();
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
  }

  @FXML
  void onAddIncome(ActionEvent event) {}

  @FXML
  void switchToPreviousFromAddIncomeDialog(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().add(css);
    stage.show();
    savingsPlanController.start();
  }
}
