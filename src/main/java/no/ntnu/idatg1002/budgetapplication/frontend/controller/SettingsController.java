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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ConfirmationAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

public class SettingsController implements Initializable {

  @FXML private JFXButton backToMenuButton;
  @FXML private JFXButton editProfileButton;
  @FXML private JFXButton saveProfileButton;
  @FXML private CustomTextField userEmail;
  @FXML private CustomTextField userName;
  @FXML private CustomPasswordField userPassword;




  @FXML
  public void backToMenu(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    userName.setText(SessionAccount.getInstance().getAccount().getName());
    userEmail.setText(SessionAccount.getInstance().getAccount().getEmail());
    userPassword.setText(SessionAccount.getInstance().getAccount().getPinCode());
  }

  @FXML
  private void editProfile(ActionEvent event) {
    userName.setEditable(true);
    userEmail.setEditable(true);
    userPassword.setEditable(true);
  }

  @FXML
  private void saveProfile(ActionEvent event) {
    if (!userName.getText().equals(SessionAccount.getInstance().getAccount().getName()) ||
    !userEmail.getText().equals(SessionAccount.getInstance().getAccount().getEmail()) ||
    !userPassword.getText().equals(SessionAccount.getInstance().getAccount().getPinCode()) &&
    !userName.getText().isEmpty() && !userName.getText().isBlank() &&
    !userEmail.getText().isEmpty() && !userEmail.getText().isBlank() &&
    !userPassword.getText().isEmpty() && !userPassword.getText().isBlank() &&
        userPassword.getText().length() == 4) {
      ConfirmationAlert confirmationAlert = new ConfirmationAlert("Edit Profile",
          "Are You Sure You Want Save This Changes? ");
      Optional<ButtonType> result = confirmationAlert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        editedProfileTemp();
      }
    } else {

    }
  }

  private void editedProfileTemp() {
    if (!userName.equals(SessionAccount.getInstance().getAccount().getName())) {
      SessionAccount.getInstance().getAccount().setName(userName.getText());
      userName.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
    if (!userEmail.equals(SessionAccount.getInstance().getAccount().getEmail())) {
      SessionAccount.getInstance().getAccount().setName(userName.getText());
      userEmail.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
    if (!userPassword.equals(SessionAccount.getInstance().getAccount().getEmail())) {
      SessionAccount.getInstance().getAccount().setName(userName.getText());
      userPassword.setEditable(false);
      AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
    }
  }
}
