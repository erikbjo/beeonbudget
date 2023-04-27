package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;

/** Represents the controller for the first view when resetting pin. */
public class ResetPinCodeEnterUserController {
  @FXML public Text resetPinCodeText;
  @FXML public TextField emailTextField;
  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML private Text resetPasswordText; // Value injected by FXMLLoader
  @FXML private Button backToLoginButton; // Value injected by FXMLLoader
  @FXML private Button continueButton; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    configureUsernameTextField();
  }

  /**
   * Checks if the user's email is valid. If it is, the user is allowed to continue to the next
   * view.
   *
   * @param event The event that triggered the method call.
   * @throws IOException If the FXML file for the next view is not found.
   */
  @FXML
  void continueResetPassword(ActionEvent event) throws IOException {
    if (assertAllFieldsValid() && (isValidEmail(emailTextField.getText()))) {
      SessionAccount.getInstance()
          .setAccount(AccountDAO.getInstance().getAccountByEmail(emailTextField.getText()));
      switchToEnterNewPinCode(event);
    } else if (emailTextField.getText().isEmpty() || emailTextField.getText().isBlank()) {
      showEmptyTextFieldAlert();
    } else {
      showInvalidLoginAlert();
    }
  }

  /**
   * Sends the user to the reset pin code view.
   *
   * @param event The event that triggered the method call.
   * @throws IOException If the FXML file for the next view is not found.
   */
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

  /**
   * Sends the user to the login view.
   *
   * @param event the event that triggered the method call
   * @throws IOException the exception that is thrown if the FXML file for the next view is not
   */
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

  /**
   * Configures the username text field so that it is not possible to enter a space as the first
   * character.
   */
  private void configureUsernameTextField() {
    emailTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                emailTextField.clear();
              }
            });
  }

  /**
   * Checks if all the fields are valid.
   *
   * @return True if all the fields are valid, false otherwise.
   */
  private boolean assertAllFieldsValid() {
    return (!emailTextField.getText().isEmpty() && !emailTextField.getText().isBlank());
  }

  /**
   * Checks if the user's email is registered in the database.
   *
   * @param email The email to be checked.
   * @return True if the email is valid, false otherwise.
   */
  private boolean isValidEmail(String email) {
    boolean isValidEmail = false;
    List<Account> accounts = AccountDAO.getInstance().getAll();

    for (Account account : accounts) {
      if (Objects.equals(account.getEmail(), email)) {
        isValidEmail = true;
        break;
      }
    }
    return isValidEmail;
  }

  /** Shows an alert if the user has not filled out the username or email text field. */
  private void showEmptyTextFieldAlert() {
    WarningAlert warningAlert = new WarningAlert("Please fill out username or email");
    warningAlert.showAndWait();
  }

  /** Shows an alert if the user has entered an invalid username or email. */
  private void showInvalidLoginAlert() {
    WarningAlert warningAlert = new WarningAlert("Invalid username or email");
    warningAlert.showAndWait();
  }
}
