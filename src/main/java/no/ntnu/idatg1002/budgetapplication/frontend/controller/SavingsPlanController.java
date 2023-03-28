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

import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;

public class SavingsPlanController {

  private Stage stage;
  private Scene scene;
  private Parent parent;
  @FXML TextField name;
  @FXML TextField totAmount;
  @FXML TextField savAmount;
  @FXML DialogPane popup;
  @FXML Label goalName;
  private SavingsPlan example = new SavingsPlan("example", 100, 1);


  private PrimaryController primaryController = new PrimaryController();

  public void switchToPrimaryFromSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    start();
  }

  public void start(){
  goalName.setText(example.getGoalName()); //TODO make it so that the text displayed on the tabs is taken from correct object
  }



  public void onNewSavingsPlan(){
    popup.setVisible(true);
  }

  public void onAccept(){ //TODO make it add a new tab with data from created object
    String goalName = name.getText();
    int totalGoalAmount = Integer.parseInt(totAmount.getText());
    int savedAmount = Integer.parseInt(savAmount.getText());
    SavingsPlan plan = new SavingsPlan(goalName, totalGoalAmount, savedAmount);
    popup.setVisible(false);
  }

  public void onEstimateMonths(){ //TODO move estimate inputs to popup window and only display results

  }



}
