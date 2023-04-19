package no.ntnu.idatg1002.budgetapplication.frontend.controller;
/** Sample Skeleton for 'registerNewAccount.fxml' Controller Class */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;

public class RegisterNewAccountController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="emailText"
  private Text emailText; // Value injected by FXMLLoader
  @FXML // fx:id="pinCodeText"
  private Text pinCodeText; // Value injected by FXMLLoader
  @FXML // fx:id="registerNewAccountText"
  private Text registerNewAccountText; // Value injected by FXMLLoader
  @FXML // fx:id="securityQuestionAnswerText"
  private Text securityQuestionAnswerText; // Value injected by FXMLLoader
  @FXML // fx:id="securityQuestionText"
  private Text securityQuestionText; // Value injected by FXMLLoader
  @FXML // fx:id="usernameText"
  private Text usernameText; // Value injected by FXMLLoader

  @FXML // fx:id="securityQuestionAnswerTextField"
  private TextField securityQuestionAnswerTextField; // Value injected by FXMLLoader
  @FXML // fx:id="usernameTextField"
  private TextField usernameTextField; // Value injected by FXMLLoader
  @FXML // fx:id="pinCodeTextField"
  private TextField pinCodeTextField; // Value injected by FXMLLoader
  @FXML // fx:id="emailTextField"
  private TextField emailTextField; // Value injected by FXMLLoader

  @FXML // fx:id="registerNewAccountButton"
  private Button registerNewAccountButton; // Value injected by FXMLLoader
  @FXML // fx:id="securityQuestionComboBox"
  private ComboBox<SecurityQuestion> securityQuestionComboBox; // Value injected by FXMLLoader

  @FXML
  void registerNewAccount(ActionEvent event) {}

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert emailText != null
        : "fx:id=\"emailText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert emailTextField != null
        : "fx:id=\"emailTextField\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert pinCodeText != null
        : "fx:id=\"pinCodeText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert pinCodeTextField != null
        : "fx:id=\"pinCodeTextField\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert registerNewAccountButton != null
        : "fx:id=\"registerNewAccountButton\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert registerNewAccountText != null
        : "fx:id=\"registerNewAccountText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert securityQuestionAnswerText != null
        : "fx:id=\"securityQuestionAnswerText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert securityQuestionAnswerTextField != null
        : "fx:id=\"securityQuestionAnswerTextField\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert securityQuestionComboBox != null
        : "fx:id=\"securityQuestionComboBox\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert securityQuestionText != null
        : "fx:id=\"securityQuestionText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert usernameText != null
        : "fx:id=\"usernameText\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
    assert usernameTextField != null
        : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'registerNewAccount.fxml'.";
  }
}
