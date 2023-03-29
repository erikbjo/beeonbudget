package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;

public class SavingsPlanController {

  private Stage stage;
  private Scene scene;
  private Parent parent;
  private PrimaryController primaryController;
  @FXML TextField name;
  @FXML TextField totAmount;
  @FXML Label estimateMoneyDisplay;
  @FXML Label estimateMonthsDisplay;
  @FXML TextField savAmount;
  @FXML TextField estimateMonths;
  @FXML TextField estimateMoney;
  @FXML DialogPane popup;
  @FXML Label goalNameDisplay;
  @FXML TabPane tabPane;
  @FXML TextField depositInput;
  @FXML Button deposit;
  @FXML Label totalGoalAmountDisplay;
  @FXML Label totalAmountSavedDisplay;
  private SavingsPlan example = new SavingsPlan("example", 100, 1);


  public SavingsPlanController() throws IOException {
    primaryController = new PrimaryController();
  }

  public void switchToPrimaryFromSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }


  public void onNewSavingsPlan(){
    int index = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());
    Tab newTab = new Tab();
    tabPane.getTabs().add(newTab);
  }

  public void onDeposit(){
    example.setDeposit(Integer.parseInt(depositInput.getText()));
    example.addSavings();
  }


  public void onEdit(){
    popup.setVisible(true);
  }

  public void onAccept(){
    String goalName = name.getText();
    int totalGoalAmount = Integer.parseInt(totAmount.getText());
    int savedAmount = Integer.parseInt(savAmount.getText());
    int estMonths = Integer.parseInt(estimateMonths.getText());
    int estMoney = Integer.parseInt(estimateMoney.getText());
    SavingsPlan plan = new SavingsPlan(goalName, totalGoalAmount, savedAmount);
    plan.setWantedMonthlySavingAmount(estMoney);
    plan.setWantedSavingTime(estMonths);
    int savingAmount = plan.getEstimatedMonthlySavingAmount();
    int savingTime = plan.getEstimatedSavingTime();
    totalGoalAmountDisplay.setText("Total Goal Amount: "+totalGoalAmount);
    totalAmountSavedDisplay.setText("Total Amount Saved: "+savedAmount);
    goalNameDisplay.setText(example.getGoalName());
    estimateMoneyDisplay.setText("Amount you need to be saving per month: "+savingAmount);
    estimateMonthsDisplay.setText("Months until completion: "+savingTime);
    popup.setVisible(false);
  }
//TODO make it add a new tab with data from created object
  }





