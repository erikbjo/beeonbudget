package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.ExceptionAlert;
import no.ntnu.idatg1002.budgetapplication.frontend.alerts.WarningAlert;

/**
 * Represents a custom dialog for adding a budget in the budget application. The dialog includes a
 * field for entering the budget name.
 *
 * @author Erik Bjørnsen
 * @version 1.1
 */
public class AddBudgetDialog extends Dialog<Budget> {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Label budgetNameText;
  @FXML private TextField budgetNameTextField;
  @FXML private Button cancelButton;
  @FXML private Button submitButton;
  @FXML private DatePicker endDatePicker;
  @FXML private DatePicker startDatePicker;

  /** Constructs an AddBudgetDialog, loading the FXML and configuring the budget name text field. */
  public AddBudgetDialog() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addBudgetDialog.fxml"));
    loader.setController(this);
    DialogPane dialogPane = new DialogPane();
    try {
      dialogPane.setContent(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/dialog.css"))
            .toExternalForm();
    dialogPane.getStylesheets().add(css);

    this.setDialogPane(dialogPane);
    this.setTitle("Add Budget");
    startDatePicker.setValue(LocalDate.now());

    configureDatePickers();
    configureBudgetNameAmountField();
  }

  /** Configures the budget name text field to prevent starting with a space. */
  private void configureBudgetNameAmountField() {
    budgetNameTextField
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                budgetNameTextField.clear();
              }
            });
  }

  /**
   * Configures the date picker to open on focus.
   */
  private void configureDatePickers() {
    startDatePicker
        .focusedProperty()
        .addListener(
            (observableValue, oldPropertyValue, newPropertyValue) -> {
              if (Boolean.TRUE.equals(newPropertyValue)) {
                startDatePicker.show();
              } else {
                startDatePicker.hide();
              }
            });
    endDatePicker
        .focusedProperty()
        .addListener(
            (observableValue, oldPropertyValue, newPropertyValue) -> {
              if (Boolean.TRUE.equals(newPropertyValue)) {
                endDatePicker.show();
              } else {
                endDatePicker.hide();
              }
            });
  }

  /**
   * Closes the AddBudgetDialog when the "Cancel" button is clicked.
   *
   * @param event the action event triggered by clicking the "Cancel" button
   */
  @FXML
  void closeDialog(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Handles the "Submit" button click, creating a new budget if all fields are valid. Displays
   * dynamic feedback if the fields are invalid.
   *
   * @param event the action event triggered by clicking the "Submit" button
   */
  @FXML
  void handleSubmit(ActionEvent event) {
    Budget newBudget;
    if (assertAllFieldsValid()) {
      try {
        newBudget = new Budget(budgetNameTextField.getText(),
            getStartDate(),
            getEndDate());
        this.setResult(newBudget);
        this.close();
      } catch (Exception exception) {
        ExceptionAlert exceptionAlert = new ExceptionAlert(exception);
        exceptionAlert.showAndWait();
      }
    } else {
      generateFeedbackAlert();
    }
  }

  /**
   * Checks if all fields in the dialog are valid for budget creation.
   *
   * @return true if all fields are valid, false otherwise
   */
  private boolean assertAllFieldsValid() {
    return !budgetNameTextField.getText().isEmpty() && !budgetNameTextField.getText().isBlank()
        && !startDatePicker.getEditor().getText().isEmpty()
        && !endDatePicker.getEditor().getText().isEmpty()
        && getEndDate().isAfter(getStartDate());
  }

  /** Generates feedback for the user if the budget name field is invalid. */
  private void generateFeedbackAlert() {
    StringBuilder builder = new StringBuilder("Something is wrong: \n");
    if (getBudgetNameTextField().isEmpty() || getBudgetNameTextField().isBlank()) {
      builder.append("Name is Empty Or Blank \n");
    }
    if (getEndDatePicker().getValue() == null) {
      builder.append("You Need To chose a End Date \n");
    }
    if (getEndDate().isBefore(getStartDate()) && getEndDatePicker().getValue() != null) {
      builder.append("End Date Cant Be Before Start Date \n");
    }
    WarningAlert warningAlert = new WarningAlert();
    warningAlert.setContentText(builder.toString());
    warningAlert.initOwner(this.getDialogPane().getScene().getWindow());
    warningAlert.showAndWait();
  }

  /**
   * Returns the endDatePicker.
   *
   * @return the endDatePicker.
   */
  public DatePicker getEndDatePicker() {
    return endDatePicker;
  }

  /**
   * Returns the startDatePicker.
   *
   * @return the startDatePicker.
   */
  public DatePicker getStartDatePicker() {
    return startDatePicker;
  }

  /**
   * Returns the budget name from budgetNameTextField.
   *
   * @return the budget name from budgetNameTextField as a String.
   */
  public String getBudgetNameTextField() {
    return budgetNameTextField.getText();
  }

  /**
   * Returns the selected value in the startDatePicker.
   *
   * @return the selected value in the startDatePicker as LocalDate.
   */
  public LocalDate getStartDate() {
    return getStartDatePicker().getValue();
  }

  /**
   * Returns end date from EndDatePicker.
   *
   * @return end date from EndDatePicker as LocalDate.
   */
  public LocalDate getEndDate() {
    if (getEndDatePicker().getValue() == null) {
      WarningAlert alert = new WarningAlert(""
          + "End Date Is Empty");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.showAndWait();
    }
    return getEndDatePicker().getValue();
  }
}
