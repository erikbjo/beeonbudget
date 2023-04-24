package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ConfirmationAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddSavingsDepositDialog;
import no.ntnu.idatg1002.budgetapplication.frontend.dialogs.AddSavingsPlanDialog;

/** This class is the controller for SavingsPlan GUI
 *
 * @author Igor Dzugaj
 * @version 1.0
 */
public class SavingsPlanController implements Initializable{

  @FXML
  public ProgressIndicator goalProgressIndicator;
  @FXML
  public Label userNameInSavingsPlan;
  @FXML
  public Label planNameInSavingsPlan;
  @FXML
  public Label savingsPlanDateLabel;
  @FXML
  public Label totalSavedLabel;
  @FXML
  public Label totalLeftLabel;
  @FXML
  public Label goalLabel;
  private Stage stage;
  private Scene scene;
  private Parent parent;
  private final PrimaryController primaryController;

  /**
   * A constructor for the SavingsPlanController class.
   *
   * @throws IOException If an input or output exception occurs
   */
  public SavingsPlanController() throws IOException {
    primaryController = new PrimaryController();
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
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  @FXML
  public void onNewSavingsPlan(ActionEvent event) {
    AddSavingsPlanDialog dialog = new AddSavingsPlanDialog();
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

    Optional<SavingsPlan> result = dialog.showAndWait();
    result.ifPresent(savingsPlan -> SessionAccount.getInstance().getAccount().addSavingsPlan(savingsPlan));
    AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    updateAllInSavingsPlan();
  }

  /**
   * Updates the plan's deposit and total savings amount and displays the new total amount saved.
   */
  public void onDeposit(ActionEvent event) throws IOException {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      AddSavingsDepositDialog dialog = new AddSavingsDepositDialog();
      dialog.initOwner(((Node) event.getSource()).getScene().getWindow());

      Optional<Integer> result = dialog.showAndWait();
      result.ifPresent(
          deposit -> {
            SessionAccount.getInstance()
                    .getAccount().getSelectedSavingsPlan().deposit(deposit);
            updateAllInSavingsPlan();
            AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
          });
    } else {
      showNoSavingsPlanErrorFromNewMoneyAction();
    }
  }

  /** Displays the edit popup to allow users to change details of their savings plan. */
  public void onEdit() {
    throw new UnsupportedOperationException();
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateAllInSavingsPlan();
  }

  @FXML
  public void onNextSavingsPlan(ActionEvent event) {
    goalProgressIndicator.setProgress(goalProgressIndicator.getProgress() + 0.1);
  }

  @FXML
  public void onPreviousSavingsPlan(ActionEvent event) {
    throw new UnsupportedOperationException();
  }

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
        SessionAccount.getInstance().getAccount().selectPreviousSavingsPlan();
        updateAllInSavingsPlan();
        AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
      }
    } else {
      showNoSavingsPlanErrorFromDeleteSavingsPlan();
    }
  }

  @FXML
  public void switchToPrimaryFromBudgetMouseEvent(MouseEvent mouseEvent) {
    throw new UnsupportedOperationException();
  }

  /** This method calls all the other update methods, so that the entire UI is updated. */
  private void updateAllInSavingsPlan() {
    updateProgressIndicator();
    updateSavingsPlanInfoText();
    updateSavingsPlanMoneyText();
    updateProgressIndicator();
  }

  public void updateSavingsPlanInfoText() {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      planNameInSavingsPlan.setText(
          SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getGoalName());
      userNameInSavingsPlan.setText(SessionAccount.getInstance().getAccount().getName());
    } else {
      setDefaultSavingsPlanInfoText();
    }
  }

  public void updateSavingsPlanMoneyText() {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      totalSavedLabel.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalSaved()));
      totalLeftLabel.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalGoalAmount()
          - SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalSaved()));
      goalLabel.setText(
          String.valueOf(
              SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalGoalAmount()));
      savingsPlanDateLabel.setText(
          "Start Date: " + SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
              .getStartDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
              + "  End Date: " + SessionAccount.getInstance().getAccount().getSelectedSavingsPlan()
              .getEndDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    } else {
      setDefaultSavingsPlanMoneyText();
    }
  }

  public void updateProgressIndicator() {
    if (SessionAccount.getInstance().getAccount().getCurrentSavingsPlanIndex() != null) {
      goalProgressIndicator.setProgress(SessionAccount.getInstance().getAccount().getSelectedSavingsPlan().getTotalSavedPercentage());
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
