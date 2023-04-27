package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ConfirmationAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ExceptionAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

/** Represents the controller for the settings view.
 *
 * @author Eskil Alstad
 */
public class SettingsController implements Initializable {

  @FXML private JFXButton backToMenuButton;
  @FXML private JFXButton editProfileButton;
  @FXML private JFXButton saveProfileButton;
  @FXML private CustomTextField userEmail;
  @FXML private CustomTextField userName;
  @FXML private CustomPasswordField userPassword;

  /**
   * Button that when pressed changes the view to the primary view.
   *
   * @param event The event that triggered the method.
   * @throws IOException if the primary.fxml file cannot be loaded.
   */
  @FXML
  public void backToMenu(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * This method is called by the FXMLLoader when initialization is complete.
   *
   * @param url not used
   * @param resourceBundle not used
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    userName.setText(SessionAccount.getInstance().getAccount().getName());
    userEmail.setText(SessionAccount.getInstance().getAccount().getEmail());
    userPassword.setText(SessionAccount.getInstance().getAccount().getPinCode());
    configurePinCodeTextField();
  }

  /**
   * Disables the save profile button and makes the text fields editable.
   *
   * @param event the event that triggered the method
   */
  @FXML
  private void editProfile(ActionEvent event) {
    saveProfileButton.setDisable(false);
    userName.setEditable(true);
    userEmail.setEditable(true);
    userPassword.setEditable(true);
  }

  /**
   * @param event the event that triggered the method
   */
  @FXML
  private void saveProfile(ActionEvent event) {
    if (assertAllFieldsValid()) {
      try {
        ConfirmationAlert confirmationAlert =
            new ConfirmationAlert("Edit Profile", "Are You Sure You Want Save This Changes? ");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
          editedProfileTemp();
          saveProfileButton.setDisable(true);
        }
      } catch (IllegalArgumentException exception) {
        ExceptionAlert exceptionAlert = new ExceptionAlert(exception);
        exceptionAlert.showAndWait();
      }

    } else {
      generateDynamicFeedbackAlert();
    }
  }

  /** Updates the account information in the database. */
  private void editedProfileTemp() {
    if (!userName.getText().equals(SessionAccount.getInstance().getAccount().getName())) {
      SessionAccount.getInstance().getAccount().setName(userName.getText());
      userName.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
    if (!userEmail.getText().equals(SessionAccount.getInstance().getAccount().getEmail())) {
      SessionAccount.getInstance().getAccount().setEmail(userEmail.getText());
      userEmail.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
    if (!userPassword.getText().equals(SessionAccount.getInstance().getAccount().getEmail())) {
      SessionAccount.getInstance().getAccount().setPinCode(userPassword.getText());
      userPassword.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
  }

  /**
   * Checks if all the fields are valid.
   *
   * @return true if all the fields are valid, false otherwise.
   */
  private boolean assertAllFieldsValid() {
    return (!userName.getText().isEmpty()
        && !userName.getText().isBlank()
        && !userEmail.getText().isEmpty()
        && !userEmail.getText().isBlank()
        && !userPassword.getText().isEmpty()
        && !userPassword.getText().isBlank());
  }

  /** Generates a dynamic feedback alert that tells the user which fields are not valid. */
  @FXML
  private void generateDynamicFeedbackAlert() {
    StringBuilder builder = new StringBuilder("Not Valid : \n");
    if (userName.getText().isEmpty() || userName.getText().isBlank()) {
      builder.append("Name, Cant be Blank or Empty \n");
    }
    if (userEmail.getText().isEmpty() || userEmail.getText().isBlank()) {
      builder.append("Email \n");
    }
    if (userPassword.getText().isEmpty() || userPassword.getText().isBlank()) {
      builder.append("Pin code \n");
    }
    WarningAlert warningAlert = new WarningAlert();
    warningAlert.setContentText(builder.toString());
    warningAlert.showAndWait();
  }

  /** Configures the pin code text field to only accept numeric values and max 4 digits. */
  private void configurePinCodeTextField() {
    userPassword
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              // numeric only
              if (!newValue.matches("\\d*")) {
                userPassword.setText(newValue.replaceAll("[^\\d]", ""));
              }
              // max 4 digits
              if (newValue.length() > 4) {
                userPassword.setText(oldValue);
              }
            });
  }
}
