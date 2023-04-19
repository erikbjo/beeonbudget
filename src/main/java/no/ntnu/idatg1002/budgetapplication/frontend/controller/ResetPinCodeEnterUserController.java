package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ResetPinCodeEnterUserController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="budgetApplicationText"
  private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML // fx:id="resetPasswordText"
  private Text resetPasswordText; // Value injected by FXMLLoader
  @FXML // fx:id="usernameText"
  private Text usernameText; // Value injected by FXMLLoader

  @FXML // fx:id="usernameTextField"
  private TextField usernameTextField; // Value injected by FXMLLoader

  @FXML // fx:id="backToLoginButton"
  private Button backToLoginButton; // Value injected by FXMLLoader
  @FXML // fx:id="continueButton"
  private Button continueButton; // Value injected by FXMLLoader

  @FXML
  void continueResetPassword(ActionEvent event) {}

  @FXML
  void goBackToLogin(ActionEvent event) {}

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert backToLoginButton != null
        : "fx:id=\"backToLoginButton\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
    assert budgetApplicationText != null
        : "fx:id=\"budgetApplicationText\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
    assert continueButton != null
        : "fx:id=\"continueButton\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
    assert resetPasswordText != null
        : "fx:id=\"resetPasswordText\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
    assert usernameText != null
        : "fx:id=\"usernameText\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
    assert usernameTextField != null
        : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'resetPinCodeEnterUser.fxml'.";
  }
}
