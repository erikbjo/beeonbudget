package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

  private Stage stage;
  private Scene scene;
  private Parent parent;

  public void switchToBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/budget.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToSavingsPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/savingsPlan.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
