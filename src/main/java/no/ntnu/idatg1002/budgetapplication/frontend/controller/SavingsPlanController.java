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

/**
 * This class is the controller for SavingsPlan GUI
 */
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
  private SavingsPlan plan = new SavingsPlan("example");

  /**
   A constructor for the SavingsPlanController class.

   @throws IOException If an input or output exception occurs
   */
  public SavingsPlanController() throws IOException {
    primaryController = new PrimaryController();
  }

  /**
   Switches the view to the primary view from the savings plan view.

   @param event The event triggering the method call
   @throws IOException If an input or output exception occurs
   */
  public void switchToPrimaryFromSavingPlan(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/primary.fxml"));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }


  /**
   Creates a new tab for a new savings plan.
   */
  public void onNewSavingsPlan(){
    int index = tabPane.getTabs().indexOf(tabPane.getSelectionModel().getSelectedItem());
    Tab newTab = new Tab();
    tabPane.getTabs().add(newTab);
  }

  /**
   Updates the plan's deposit and total savings amount and displays the new total amount saved.
   */
  public void onDeposit(){
    plan.setDeposit(Integer.parseInt(depositInput.getText()));
    plan.addSavings();
    totalAmountSavedDisplay.setText("Total Amount Saved: \n"+plan.getTotalSaved());
  }

  /**
   Displays the edit popup to allow users to change details of their savings plan.
   */
  public void onEdit(){
    popup.setVisible(true);
  }

  /**
   Accepts changes made to the savings plan and updates the plan's details and displays them.
   */
  public void onAccept(){
    String goalName = name.getText();
    int totalGoalAmount = Integer.parseInt(totAmount.getText());
    int savedAmount = Integer.parseInt(savAmount.getText());
    int estMonths = Integer.parseInt(estimateMonths.getText());
    int estMoney = Integer.parseInt(estimateMoney.getText());
    plan.setGoalName(goalName);
    plan.setTotalGoalAmount(totalGoalAmount);
    plan.setTotalSaved(savedAmount);
    plan.setWantedMonthlySavingAmount(estMoney);
    plan.setWantedSavingTime(estMonths);
    int savingAmount = plan.getEstimatedMonthlySavingAmount();
    int savingTime = plan.getEstimatedSavingTime();
    totalGoalAmountDisplay.setText("Total Goal Amount: \n"+totalGoalAmount);
    totalAmountSavedDisplay.setText("Total Amount Saved: \n"+savedAmount);
    goalNameDisplay.setText(plan.getGoalName());
    estimateMoneyDisplay.setText("Amount you need to \nbe saving per month: \n"+savingAmount);
    estimateMonthsDisplay.setText("Months until \ncompletion: \n"+savingTime);
    popup.setVisible(false);
  }
  }





