package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

/**
 * Controller for resetting the user's pin code by answering the security question.
 *
 * @author Erik Bjørnsen, Simon Husås Houmb
 * @version 1.1
 */
public class ResetPinCodeEnterNewPinCodeController {

  @FXML public TextField emailTextField;
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

  /** Initializes the controller and sets up the necessary components. */
  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    setEmail();
    setSecurityQuestion();
    configurePinCodeTextField();
    configureSecurityQuestionAnswerTextField();
  }

  /** Sets the email field to the user's email. */
  public void setEmail() {
    try {
      emailTextField.setText(SessionAccount.getInstance().getAccount().getEmail());
    } catch (Exception e) {
      e.printStackTrace();
      emailTextField.setText("Invalid");
    }
  }

  /** Sets the security question field to the user's security question. */
  private void setSecurityQuestion() {
    try {
      securityQuestionTextField.setText(
          SessionAccount.getInstance()
              .getAccount()
              .getSecurityQuestion()
              .getSecurityQuestionString());
    } catch (Exception e) {
      e.printStackTrace();
      securityQuestionTextField.setText("Invalid");
    }
  }

  /** Configures the pin code text field to only accept numeric input with a maximum of 4 digits. */
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

  /** Configures the security question answer text field to avoid unnecessary whitespace. */
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

  /** Navigates back to the login screen. */
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

  /** Validates the fields and sets the new pin code if the security question answer is correct. */
  @FXML
  void setNewPinCode(ActionEvent event) throws IOException {
    if (assertAllFieldsValid()) {
      if (isAnswerValid()) {
        setPinCode(pinCodeTextField.getText());
        AccountDAO.getInstance().update(SessionAccount.getInstance().getAccount());
        goToLogin(event);
      } else {
        wrongAnswerAlert();
      }
    } else {
      generateDynamicFeedbackAlert();
    }
  }

  /** Goes to the login screen after successfully setting the new pin code. */
  private void goToLogin(Event event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * Checks if all fields are valid.
   *
   * @return true if all fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!pinCodeTextField.getText().isEmpty()
        && !pinCodeTextField.getText().isBlank()
        && pinCodeTextField.getText().length() == 4
        && !securityQuestionAnswerTextField.getText().isEmpty()
        && !securityQuestionAnswerTextField.getText().isBlank());
  }

  /**
   * Validates the security question answer.
   *
   * @return true if the answer is valid, false otherwise
   */
  private boolean isAnswerValid() {
    boolean isValid = false;
    String answer = securityQuestionAnswerTextField.getText();

    if (answer.equalsIgnoreCase(SessionAccount.getInstance().getAccount().getSecurityAnswer())) {
      isValid = true;
    }
    return isValid;
  }

  /**
   * Sets the new pin code for the user's account.
   *
   * @param pinCode the new pin code to set
   */
  private void setPinCode(String pinCode) {
    SessionAccount.getInstance().getAccount().setPinCode(pinCode);
  }

  /** Displays an alert when the security question answer is incorrect. */
  @FXML
  private void wrongAnswerAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText("Wrong answer given");
    alert.setContentText("Please enter a valid answer.");
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.showAndWait();
  }

  /** Generates and displays a dynamic feedback alert when fields are not filled out correctly. */
  @FXML
  private void generateDynamicFeedbackAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    StringBuilder builder = new StringBuilder("Please fill out the following field(s): \n");

    if (securityQuestionAnswerTextField.getText().isEmpty()
        || securityQuestionAnswerTextField.getText().isBlank()) {
      builder.append("Security question answer \n");
    }
    if (pinCodeTextField.getText().isEmpty() || pinCodeTextField.getText().isBlank()) {
      builder.append("Pin code \n");
    } else if (pinCodeTextField.getText().length() < 4) {
      builder.append("Full pin code \n");
    }

    alert.setContentText(builder.toString());
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.showAndWait();
  }
}
