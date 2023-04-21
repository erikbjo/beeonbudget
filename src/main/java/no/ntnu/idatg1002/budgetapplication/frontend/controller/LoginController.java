package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

public class LoginController {

  @FXML public Text emailText;
  @FXML public TextField emailTextField;
  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;
  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;
  @FXML private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML private Text loginOrRegisterText; // Value injected by FXMLLoader
  @FXML private Text pinCodeText; // Value injected by FXMLLoader
  @FXML private PasswordField pinCodeTextField; // Value injected by FXMLLoader
  @FXML private Hyperlink forgotPinCodeHyperlink; // Value injected by FXMLLoader
  @FXML private Button loginButton; // Value injected by FXMLLoader
  @FXML private Button registerNewAccountButton; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    SessionAccount.getInstance().clearAccount();
    configurePinCodeTextField();
    configureEmailTextField();
  }

  /**
   * Handles the "Forgot Pin Code" hyperlink click, navigating to the Reset Pin Code screen.
   *
   * @param event the event associated with the hyperlink click
   * @throws IOException if there is an issue loading the FXML file
   */
  @FXML
  void forgotPinCode(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/fxmlfiles/resetPinCodeEnterUser.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * Logs the user in when the "Login" button is clicked, navigating to the Primary screen. Displays
   * an invalid login alert or dynamic feedback alert if necessary.
   *
   * @param event the event associated with the button click
   * @throws IOException if there is an issue loading the FXML file
   */
  @FXML
  void loginAccount(ActionEvent event) throws IOException {
    if (assertAllFieldsValid()) {
      if (AccountDAO.getInstance()
          .loginIsValid(emailTextField.getText(), pinCodeTextField.getText())) {
        SessionAccount.getInstance()
            .setAccount(AccountDAO.getInstance().getAccountByEmail(emailTextField.getText()));
        goToPrimaryScreen((event));
      } else {
        showInvalidLoginAlert();
      }
    } else {
      generateDynamicFeedbackAlert();
    }
  }

  /**
   * Navigates to the Primary screen.
   *
   * @param event the event associated with the navigation
   * @throws IOException if there is an issue loading the FXML file
   */
  private void goToPrimaryScreen(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/primary.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * Handles the "Register New Account" button click, navigating to the Register New Account screen.
   *
   * @param event the event associated with the button click
   * @throws IOException if there is an issue loading the FXML file
   */
  @FXML
  void registerNewAccount(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/fxmlfiles/registerNewAccount.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  /**
   * Validates the email and pin code fields.
   *
   * @return true if both fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return (!emailTextField.getText().isEmpty() && pinCodeTextField.getText().length() == 4);
  }

  /** Configures the pin code TextField to accept only numeric input and limit input to 4 digits. */
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

  /** Configures the email TextField to prevent input of leading spaces. */
  private void configureEmailTextField() {
    emailTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                emailTextField.clear();
              }
            });
  }

  /** Displays an invalid login alert if the username or pin code is incorrect. */
  private void showInvalidLoginAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Invalid username or pin code");
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  /** Generates and displays a dynamic feedback alert if any of the required fields are invalid. */
  private void generateDynamicFeedbackAlert() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    StringBuilder builder = new StringBuilder("Please fill out the following field(s): \n");

    if (emailTextField.getText().isEmpty()) {
      builder.append("Email \n");
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

  public void onPinCodeTextFieldKeyPressed(KeyEvent keyEvent) {
  }
}
