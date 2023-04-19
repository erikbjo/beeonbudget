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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ResetPinCodeEnterNewPinCodeController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="budgetApplicationText"
  private Text budgetApplicationText; // Value injected by FXMLLoader
  @FXML // fx:id="newPinCodeText"
  private Text newPinCodeText; // Value injected by FXMLLoader
  @FXML // fx:id="resetPasswordText"
  private Text resetPasswordText; // Value injected by FXMLLoader
  @FXML // fx:id="securityQuestionText"
  private Text securityQuestionText; // Value injected by FXMLLoader

  @FXML // fx:id="securityQuestionAnswerTextField"
  private TextField securityQuestionAnswerTextField; // Value injected by FXMLLoader
  @FXML // fx:id="securityQuestionTextField"
  private TextField securityQuestionTextField; // Value injected by FXMLLoader
  @FXML // fx:id="pinCodeTextField"
  private TextField pinCodeTextField; // Value injected by FXMLLoader

  @FXML // fx:id="setNewPinCodeButton"
  private Button setNewPinCodeButton; // Value injected by FXMLLoader
  @FXML // fx:id="backToLoginButton"
  private Button backToLoginButton; // Value injected by FXMLLoader

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
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxmlfiles/login.fxml")));
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().add(css);
    scene.setRoot(root);
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert backToLoginButton != null
        : "fx:id=\"backToLoginButton\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert budgetApplicationText != null
        : "fx:id=\"budgetApplicationText\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert newPinCodeText != null
        : "fx:id=\"newPinCodeText\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert pinCodeTextField != null
        : "fx:id=\"pinCodeTextField\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert resetPasswordText != null
        : "fx:id=\"resetPasswordText\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert securityQuestionAnswerTextField != null
        : "fx:id=\"securityQuestionAnswerTextField\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert securityQuestionText != null
        : "fx:id=\"securityQuestionText\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert securityQuestionTextField != null
        : "fx:id=\"securityQuestionTextField\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
    assert setNewPinCodeButton != null
        : "fx:id=\"setNewPinCodeButton\" was not injected: check your FXML file 'resetPinCodeEnterNewPinCode.fxml'.";
  }
}
