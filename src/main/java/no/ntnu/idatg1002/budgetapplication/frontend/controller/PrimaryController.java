package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;

public class PrimaryController extends Dialog<Budget> {

  @FXML private static Label menuPaneLabel1;

  @FXML private static Label menuPaneLabel2;

  private Stage stage;
  private Scene scene;

  public PrimaryController() throws IOException {
    super();
    Stage stage = (Stage) getDialogPane().getScene().getWindow();
    stage
        .getIcons()
        .add(new Image(getClass().getResource("/images/simpleLogoBoY.png").openStream()));
  }

  public void switchToBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/budget.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String css = this.getClass().getResource("/cssfiles/budget.css").toExternalForm();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddIncome(ActionEvent event) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addIncomeDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }

  public void onAddExpense(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
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
    stage.show();
  }
}
