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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

public class ResetPinCodeEnterUserController {
  @FXML
  public Text resetPinCodeText;
  @FXML
  public TextField emailTextField;
  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML private Text resetPasswordText; // Value injected by FXMLLoader

  @FXML private Button backToLoginButton; // Value injected by FXMLLoader
  @FXML private Button continueButton; // Value injected by FXMLLoader

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    configureUsernameTextField();
  }

  @FXML
  void continueResetPassword(ActionEvent event) throws IOException {
    if (assertAllFieldsValid() && (isValidUser(emailTextField.getText()))) {
      SessionAccount.getInstance()
          .setAccount(AccountDAO.getInstance().getAccountByEmail(emailTextField.getText()));
      switchToEnterNewPinCode(event);
    } else if (emailTextField.getText().isEmpty()) {
      showEmptyTextFieldAlert();
    } else {
      showInvalidLoginAlert();
    }
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
    emailTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                emailTextField.clear();
              }
            });
  }

  private boolean isValidUser(String user) {
    boolean valid = false;

    if (isEmail(user)) {
      valid = isValidEmail(user);
    }

    return valid;
  }

  private boolean assertAllFieldsValid() {
    return (!emailTextField.getText().isEmpty());
  }

  private boolean isEmail(String stringToBeChecked) {
    return stringToBeChecked.contains("@");
  }

  private boolean isValidEmail(String email) {
    boolean isValidEmail = false;
    List<Account> accounts = AccountDAO.getInstance().getAllAccounts();

    for (Account account : accounts) {
      if (Objects.equals(account.getEmail(), email)) {
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
