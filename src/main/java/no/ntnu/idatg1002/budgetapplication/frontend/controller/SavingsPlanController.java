package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Popup;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import no.ntnu.idatg1002.budgetapplication.backend.savings.SavingsPlan;

public class SavingsPlanController {

  private Stage stage;
  private Scene scene;
  private Parent parent;
  private Popup popup;



  public void switchToPrimaryFromSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void examplePlan(){
    SavingsPlan examplePlan = new SavingsPlan("SavingsPlan1", 100, 1);
    examplePlan.setWantedSavingTime(1);
    examplePlan.setWantedMonthlySavingAmount(1);
  }

  public void onNewSavingsPlan(){
    Popup popup = new Popup();

  }

  public void onEstimateMonths(){

  }



}
