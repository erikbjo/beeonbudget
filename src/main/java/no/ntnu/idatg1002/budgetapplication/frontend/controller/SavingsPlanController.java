package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ConfirmationAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddSavingsDepositDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddSavingsPlanDialog;

/**
 * This class is the controller for SavingsPlan GUI.
 *
 * @author Igor Dzugaj, Simon Husås Houmb
 * @version 1.0
 */
public class SavingsPlanController {

  @FXML public ProgressIndicator goalProgressIndicator;
  @FXML public Label userNameInSavingsPlan;
  @FXML public Label planNameInSavingsPlan;
  @FXML public Label savingsPlanDateLabel;
  @FXML public Label totalSavedLabel;
  @FXML public Label totalLeftLabel;
  @FXML public Label goalLabel;

  @FXML private JFXButton editButton;
  private Stage stage;
  private Scene scene;
  private Parent parent;

  /**
   * Switches to the primary view from the savings plan view.
   *
   * @param event The event that triggered the method.
   * @throws IOException If the primary.fxml file cannot be loaded.
   */
  public void switchToPrimaryFromSavingsPlanMouseEvent(MouseEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(
            getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene newScene = ((Node) event.getSource()).getScene();
    newScene.getStylesheets().add(css);
    newScene.setRoot(root);
  }

  /**
   * Switches the view to the primary view from the savings plan view.
   *
   * @param event The event triggering the method call
   * @throws IOException If an input or output exception occurs
   */
  public void switchToPrimaryFromSavingPlan(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene newScene = ((Node) event.getSource()).getScene();
    newScene.getStylesheets().add(css);
    newScene.setRoot(root);
  }

  /**
   * This method handles the event when the user clicks the "New Plan" button. It displays a
   * dialog box where the user can enter the details of the savings plan.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  public void onNewSavingsPlan(ActionEvent event) {
    AddSavingsPlanDialog dialog = new AddSavingsPlanDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<SavingsPlan> result = dialog.showAndWait();
    result.ifPresent(
        savingsPlan -> {
          updateAllInSavingsPlan();
          SessionAccount.getInstance().getAccount().addSavingsPlan(savingsPlan);
          AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
        });
  }

  /**
   * Updates the plan's deposit and total savings amount and displays the new total amount saved.
   *
   * @param event the event that triggered the method.
   */
  public void onDeposit(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      AddSavingsDepositDialog dialog = new AddSavingsDepositDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Integer> result = dialog.showAndWait();
      result.ifPresent(
          deposit -> {
            SessionAccount.getInstance()
                    .getAccount().getSelectedSavingsPlan().deposit(deposit);
            AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
            updateSavingsPlanMoneyText();
            updateProgressIndicator();
            updateSavingsPlanInfoText();
          });
    } else {
      showNoSavingsPlanErrorFromNewMoneyAction();
    }
  }

  /** Displays the edit popup to allow users to change details of their savings plan.
   * @param event the event that triggered the method.
   */
  public void onEdit(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      AddSavingsPlanDialog dialog = new AddSavingsPlanDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
      Optional<SavingsPlan> result = dialog.showAndWait();
      result.ifPresent(savingsPlan -> {
        SessionAccount.getInstance()
            .getAccount().getSelectedSavingsPlan().setGoalName(savingsPlan.getGoalName());
        SessionAccount.getInstance().getAccount()
            .getSelectedSavingsPlan().setTotalGoalAmount(savingsPlan.getTotalGoalAmount());
        SessionAccount.getInstance().getAccount()
            .getSelectedSavingsPlan().setStartDate(savingsPlan.getStartDate());
        SessionAccount.getInstance().getAccount()
            .getSelectedSavingsPlan().setEndDate(savingsPlan.getEndDate());
        AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
        updateAllInSavingsPlan();
      });
    } else {
      WarningAlert alert = new WarningAlert("You need to Create a Savings Plan");
      alert.showAndWait();
    }
  }

  /**
   * Initializes the SavingsPlanController.
   */
  @FXML
  public void initialize() {
    editButton.setDisable(true);
    updateSavingsPlanInfoText();
    updateProgressIndicator();
    updateSavingsPlanMoneyText();
  }

  /**
   * Handles the action event for the "Next Plan" button. This method attempts to select the next
   * savings plan from the current account's savings plans.
   * If successful, the savings plan view is updated to display
   * the selected savings plan's information. If there is no next savings plan,
   * a warning alert is shown to inform the user.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  public void onNextSavingsPlan(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getSavingsPlans().size() > 1) {
      if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
        try {
          SessionAccount.getInstance().getAccount().selectNextSavingsPlan();
          updateAllInSavingsPlan();
        } catch (IndexOutOfBoundsException e) {
          WarningAlert warningAlert = new WarningAlert("There is no next savings plan");
          warningAlert.showAndWait();
        }
      } else {
        showNoSavingsPlanErrorFromSelectNewSavingsPlan();
      }
    }
  }

  /**
   * Handles the action event for the "Previous Plan" button. This method attempts to select the
   * previous savings plan from the current account's savings plans.
   * If successful, the savings plan view is updated to display
   * the selected savings plan's information. If there is no previous savings plan,
   * a warning alert is shown to inform the user.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  public void onPreviousSavingsPlan(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getSavingsPlans().size() > 1) {
      if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
        try {
          SessionAccount.getInstance().getAccount().selectPreviousSavingsPlan();
          updateAllInSavingsPlan();
        } catch (IndexOutOfBoundsException e) {
          WarningAlert warningAlert = new WarningAlert("There is no previous savings plan");
          warningAlert.showAndWait();
        }
      } else {
        showNoSavingsPlanErrorFromSelectNewSavingsPlan();
      }
    }
  }

  /**
   * Deletes the currently selected savings plan if one exists.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  public void onDelete(ActionEvent event) {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      SavingsPlan savingsPlan = SessionAccount.getInstance().getAccount().getSelectedSavingsPlan();

      ConfirmationAlert confirmationAlert =
          new ConfirmationAlert(
              "Delete savings plan",
              "Are you sure you want to delete this savings plan?\n" + savingsPlan.getGoalName());

      Optional<ButtonType> result = confirmationAlert.showAndWait();
      if (result.isPresent() && (result.get() == ButtonType.OK)) {
        SessionAccount.getInstance()
            .getAccount()
            .removeSavingsPlan(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan());
        if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
          SessionAccount.getInstance().getAccount().selectPreviousSavingsPlan();
        }
        AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
        updateAllInSavingsPlan();
      }
    } else {
      showNoSavingsPlanErrorFromDeleteSavingsPlan();
    }
  }

  /** This method calls all the other update methods, so that the entire UI is updated. */
  private void updateAllInSavingsPlan() {
    updateProgressIndicator();
    updateSavingsPlanInfoText();
    updateSavingsPlanMoneyText();
  }

  /**
   * Updates text about the current savings plan in the GUI.
   */
  public void updateSavingsPlanInfoText() {
    userNameInSavingsPlan.setText(SessionAccount.getInstance().getAccount().getName());
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      planNameInSavingsPlan.setText(
          SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getGoalName());
    } else {
      setDefaultSavingsPlanInfoText();
    }
  }

  /**
   * Updates the text about the current savings plan money information.
   */
  public void updateSavingsPlanMoneyText() {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      totalSavedLabel.setText(
          Integer.toString(
              SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalSaved()));
      totalLeftLabel.setText(
          Integer.toString(
              SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalGoalAmount()
                  - SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalSaved()));
      goalLabel.setText(
          Integer.toString(
              SessionAccount.getInstance().getAccount()
                  .getSelectedSavingsPlan().getTotalGoalAmount()));
      savingsPlanDateLabel.setText(
          "Start Date: " + SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
              .getStartDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
              + "  End Date: " + SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
              .getEndDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    } else {
      setDefaultSavingsPlanMoneyText();
    }
  }

  /**
   * Updates the current savings plan progress indicator.
   */
  public void updateProgressIndicator() {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      goalProgressIndicator.setProgress(SessionAccount.getInstance().getAccount()
          .getSelectedSavingsPlan().getTotalSavedPercentage());
    } else {
      goalProgressIndicator.setProgress(0);
    }
  }

  @FXML
  private void showNoSavingsPlanErrorFromNewMoneyAction() {
    WarningAlert warningAlert =
        new WarningAlert("Please create a savings plan before depositing");
    warningAlert.showAndWait();
  }

  @FXML
  private void showNoSavingsPlanErrorFromDeleteSavingsPlan() {
    WarningAlert warningAlert = new WarningAlert("There is no budget to be deleted");
    warningAlert.showAndWait();
  }

  @FXML
  private void showNoSavingsPlanErrorFromSelectNewSavingsPlan() {
    WarningAlert warningAlert =
        new WarningAlert("Please create a savings plan before trying to switch savings plan");
    warningAlert.showAndWait();
  }

  private void setDefaultSavingsPlanInfoText() {
    String noPlanSelected = "No plan selected";
    planNameInSavingsPlan.setText(noPlanSelected);
    savingsPlanDateLabel.setText(noPlanSelected);
  }

  private void setDefaultSavingsPlanMoneyText() {
    String noPlanSelected = "No plan selected";
    totalSavedLabel.setText(noPlanSelected);
    totalLeftLabel.setText(noPlanSelected);
    goalLabel.setText(noPlanSelected);
  }
}
