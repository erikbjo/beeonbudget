package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;

public class PrimaryController extends Dialog<Budget> {

  private Stage stage;
  private Scene scene;
  private GridPane grid;
  private TextField expenseField;
  private TextField descriptionField;
  private TextField monthsField;

  public PrimaryController() {
    super();
    expenseField = new TextField();
    expenseField.textProperty().addListener((observableValue, oldValue, newValue) -> {

    });
    descriptionField = new TextField();
    descriptionField.textProperty().addListener((observableValue, oldValue, newValue) -> {});
    monthsField = new TextField();
    monthsField.textProperty().addListener((observableValue, oldValue, newValue) -> {});
    grid = new GridPane();
    grid.add(new Label("Expense Amount"), 0, 0);
    grid.add(new Label("Description"), 0, 2);
    grid.add(new Label("Monthly Expense"), 0, 4);
    grid.add(new CheckBox(),0,5);
    grid.add(new Label("Category"),0,6);

    grid.add(expenseField, 0, 1);
    grid.add(descriptionField, 0, 3);
    grid.add(monthsField,1,5);
    grid.add(new ComboBox<Category>(),0,7);
    grid.setHgap(10);
    grid.setVgap(10);

    getDialogPane().setContent(grid);
    getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
    setTitle("Add Expense");
    setHeaderText(null);
    setResizable(true);
  }

  public void switchToBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/budget.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddExpense(ActionEvent event) throws IOException {
     Optional<Budget> result = showAndWait();
     if (result.isPresent()) {
     Budget Budget = result.get();
     // Do something with the input
     }
  }

  public void switchToSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/savingsPlan.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
