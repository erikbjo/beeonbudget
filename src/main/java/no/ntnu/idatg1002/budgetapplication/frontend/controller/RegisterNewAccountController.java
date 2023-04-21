package no.ntnu.idatg1002.budgetapplication.frontend.controller;
/** Sample Skeleton for 'registerNewAccount.fxml' Controller Class */
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

public class RegisterNewAccountController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML private Text emailText; // Value injected by FXMLLoader
  @FXML private Text pinCodeText; // Value injected by FXMLLoader
  @FXML private Text registerNewAccountText; // Value injected by FXMLLoader
  @FXML private Text securityQuestionAnswerText; // Value injected by FXMLLoader
  @FXML private Text securityQuestionText; // Value injected by FXMLLoader
  @FXML private Text usernameText; // Value injected by FXMLLoader

  @FXML private TextField securityQuestionAnswerTextField; // Value injected by FXMLLoader
  @FXML private TextField usernameTextField; // Value injected by FXMLLoader
  @FXML private TextField pinCodeTextField; // Value injected by FXMLLoader
  @FXML private TextField emailTextField; // Value injected by FXMLLoader

  @FXML private Button registerNewAccountButton; // Value injected by FXMLLoader
  @FXML private Button backToLoginButton; // Value injected by FXMLLoader
  @FXML private ComboBox<String> securityQuestionComboBox; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    configureSecurityQuestionComboBox();
    for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
      securityQuestionComboBox.getItems().add(securityQuestion.getSecurityQuestionString());

      configureAllTextFields();
    }
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

  private void configureSecurityQuestionComboBox() {
      securityQuestionComboBox
          .focusedProperty()
          .addListener((observableValue, oldPropertyValue, newPropertyValue) -> {
            if (Boolean.TRUE.equals(newPropertyValue)) {
              securityQuestionComboBox.show();
            } else {
              securityQuestionComboBox.hide();
            }
          });
  }

  @FXML
  void registerNewAccount(ActionEvent event) throws IOException {
    if (assertAllFieldsValid()) {
      try {
        Account newAccount =
            new Account(
                usernameTextField.getText(),
                emailTextField.getText(),
                pinCodeTextField.getText(),
                reverseStringToSecurityQuestion(securityQuestionComboBox.getValue()),
                securityQuestionAnswerTextField.getText());
        AccountDAO.getInstance().addAccount(newAccount);
        SessionAccount.getInstance().setAccount(newAccount);
        goToLoginScreen(event);
      } catch (IllegalArgumentException e) {
        generateExceptionAlert(e);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      generateDynamicFeedbackAlert();
    }
  }

  private boolean assertAllFieldsValid() {
    return (!usernameTextField.getText().isEmpty()
        && !emailTextField.getText().isEmpty()
        && !pinCodeTextField.getText().isEmpty()
        && !securityQuestionComboBox.getValue().isEmpty()
        && !securityQuestionAnswerTextField.getText().isEmpty());
  }

  private void goToLoginScreen(Event event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    Scene scene = ((Node) event.getSource()).getScene();
    scene.setRoot(root);
  }

  private SecurityQuestion reverseStringToSecurityQuestion(String questionString) {
    for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
      if (securityQuestion.getSecurityQuestionString().equalsIgnoreCase(questionString)) {
        return securityQuestion;
      }
    }
    return null;
  }

  private void configureAllTextFields() {
    configurePinCodeTextField();
    configureSecurityQuestionAnswerTextField();
    makeTextFieldNotStartWithSpace(usernameTextField);
    makeTextFieldNotStartWithSpace(emailTextField);
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
              // need to have selected a question
              if (reverseStringToSecurityQuestion(securityQuestionComboBox.getValue()) == null
                  || (oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                securityQuestionAnswerTextField.clear();
              }
            });
  }

  private void makeTextFieldNotStartWithSpace(TextField textField) {
    textField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                textField.clear();
              }
            });
  }

  private void generateDynamicFeedbackAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    StringBuilder builder = new StringBuilder("Please fill out the following field(s): \n");

    if (usernameTextField.getText().isEmpty()) {
      builder.append("Username \n");
    }
    if (emailTextField.getText().isEmpty()) {
      builder.append("Email \n");
    }
    if (pinCodeTextField.getText().isEmpty()) {
      builder.append("Pin code \n");
    }
    if (securityQuestionComboBox.getValue() == null) {
      builder.append("Security question \n");
    }
    if (securityQuestionAnswerTextField.getText().isEmpty()) {
      builder.append("Security question answer \n");
    }

    alert.setContentText(builder.toString());
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  private void generateExceptionAlert(Exception exception) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(exception.getMessage());
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }
}
