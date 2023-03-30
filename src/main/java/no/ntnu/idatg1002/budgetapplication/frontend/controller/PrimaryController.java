package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class PrimaryController extends Dialog<Budget> implements Initializable {

  @FXML private Label menuPaneLabel1;
  @FXML private Label menuPaneLabel2;
  @FXML private Label usernameLabel;
  @FXML private Label budgetLabel;

  private Stage stage;
  private Scene scene;

  public PrimaryController() throws IOException {

  }

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

  public void onAddIncome(ActionEvent event) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddExpense(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/dialog.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/savingsPlan.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String css = this.getClass().getResource("/cssfiles/savingsPlan.css").toExternalForm();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  public void updateDynamicLabels() {
    budgetLabel.setText(String.format("Budget: %s",
        Database.getCurrentAccount().getSelectedBudget().getBudgetName()));
    usernameLabel.setText(Database.getCurrentAccount().getName());
    menuPaneLabel1.setText(String.format("Remaining: %dkr",
        Database.getCurrentAccount().getSelectedBudget().getNetBalance()));
    menuPaneLabel2.setText(String.format("Budget spent: %dkr",
        Database.getCurrentAccount().getSelectedBudget().getTotalExpense()));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateDynamicLabels();
  }
}
