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

public class ResetPinCodeEnterNewPinCodeController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML private Text newPinCodeText; // Value injected by FXMLLoader
  @FXML private Text resetPasswordText; // Value injected by FXMLLoader
  @FXML private Text securityQuestionText; // Value injected by FXMLLoader
  @FXML private Text answerText; // Value Injected by FXMLLoader

  @FXML private TextField securityQuestionAnswerTextField; // Value injected by FXMLLoader
  @FXML private TextField securityQuestionTextField; // Value injected by FXMLLoader
  @FXML private TextField pinCodeTextField; // Value injected by FXMLLoader

  @FXML private Button setNewPinCodeButton; // Value injected by FXMLLoader
  @FXML private Button backToLoginButton; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    setSecurityQuestion();
    configurePinCodeTextField();
    configureSecurityQuestionAnswerTextField();
  }

  private void setSecurityQuestion() {
    try {
      securityQuestionTextField.setText(
          Database.getCurrentAccount().getSecurityQuestion().getSecurityQuestionString());
    } catch (Exception e) {
      e.printStackTrace();
      securityQuestionTextField.setText("Invalid");
    }
  }

  private void configurePinCodeTextField() {
    pinCodeTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              // numeric only
              if (!newValue.matches("\\d*")) {
                pinCodeTextField.setText(newValue.replaceAll("[^\\d]", ""));
              }
              // max 4 digits
              if (newValue.length() > 4) {
                pinCodeTextField.setText(oldValue);
              }
            });
  }

  private void configureSecurityQuestionAnswerTextField() {
    securityQuestionAnswerTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                securityQuestionAnswerTextField.clear();
              }
            });
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

  @FXML
  void setNewPinCode(ActionEvent event) throws IOException {
    if (assertAllFieldsValid()) {
      if (isAnswerValid()) {
        setPinCode(pinCodeTextField.getText());
        goToPrimary(event);
      }
    } else {
      generateDynamicFeedbackAlert();
    }

    // FOR TESTING - REMOVE THIS
    goToPrimary(event);
    // FOR TESTING - REMOVE THIS
  }

  private void goToPrimary(Event event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  private boolean assertAllFieldsValid() {
    return (!pinCodeTextField.getText().isEmpty()
        && !securityQuestionAnswerTextField.getText().isEmpty());
  }

  private boolean isAnswerValid() {
    boolean valid = false;
    String answer = securityQuestionAnswerTextField.getText();

    var accounts = Database.getAccounts();

    for (Map.Entry<String, Account> entry : accounts.entrySet()) {
      if (Objects.equals(Database.getCurrentAccount().getSecurityAnswer(), answer)) {
        valid = true;
        break;
      }
    }

    return valid;
  }

  private void setPinCode(String pinCode) {
    Database.getCurrentAccount().setPinCode(pinCode);
  }

  private void generateDynamicFeedbackAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    StringBuilder builder = new StringBuilder("Please fill out the following field(s): \n");

    if (securityQuestionAnswerTextField.getText().isEmpty()) {
      builder.append("Security question answer \n");
    }
    if (pinCodeTextField.getText().isEmpty()) {
      builder.append("Pin code \n");
    } else {
      if (pinCodeTextField.getText().length() < 4) {
        builder.append("Full pin code \n");
      }
    }

    alert.setContentText(builder.toString());
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }
}
