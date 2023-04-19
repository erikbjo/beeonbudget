package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class ResetPinCodeEnterUserController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML private Text resetPasswordText; // Value injected by FXMLLoader
  @FXML private Text usernameText; // Value injected by FXMLLoader

  @FXML private TextField usernameTextField; // Value injected by FXMLLoader

  @FXML private Button backToLoginButton; // Value injected by FXMLLoader
  @FXML private Button continueButton; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    configureUsernameTextField();
  }

  @FXML
  void continueResetPassword(ActionEvent event) throws IOException {
    if (!usernameTextField.getText().isEmpty() && (isValidUser(usernameTextField.getText()))) {
      switchToEnterNewPinCode(event);
    } else if (usernameTextField.getText().isEmpty()) {
      showEmptyTextFieldAlert();
    } else {
      showInvalidLoginAlert();
    }

    // FOR TESTING - REMOVE THIS
    switchToEnterNewPinCode(event);
    // FOR TESTING - REMOVE THIS
  }

  private void switchToEnterNewPinCode(Event event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/fxmlfiles/resetPinCodeEnterNewPinCode.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  @FXML
  void goBackToLogin(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  private void configureUsernameTextField() {
    usernameTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                usernameTextField.clear();
              }
            });
  }

  private boolean isValidUser(String user) {
    boolean valid = false;

    if (isEmail(user)) {
      valid = isValidEmail(user);
    } else {
      valid = isValidUsername(user);
    }

    return valid;
  }

  private boolean assertAllFieldsValid() {
    return (!usernameTextField.getText().isEmpty());
  }

  private boolean isEmail(String stringToBeChecked) {
    return stringToBeChecked.contains("@");
  }

  private boolean isValidUsername(String username) {
    boolean isValidUsername = false;
    var accounts = Database.getAccounts();

    for (Map.Entry<String, Account> entry : accounts.entrySet()) {
      if (Objects.equals(entry.getValue().getName(), username)) {
        isValidUsername = true;
        break;
      }
    }

    return isValidUsername;
  }

  private boolean isValidEmail(String email) {
    boolean isValidEmail = false;
    var accounts = Database.getAccounts();

    for (Map.Entry<String, Account> entry : accounts.entrySet()) {
      if (Objects.equals(entry.getValue().getEmail(), email)) {
        isValidEmail = true;
        break;
      }
    }

    return isValidEmail;
  }

  private void showEmptyTextFieldAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Please fill out username or email");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  private void showInvalidLoginAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Invalid username or email");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }
}
