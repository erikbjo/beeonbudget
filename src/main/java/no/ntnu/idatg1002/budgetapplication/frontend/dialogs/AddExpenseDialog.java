package no.ntnu.idatg1002.budgetapplication.frontend.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;

public class AddExpenseDialog extends Dialog<HashMap> {

  // Keys for hashmap
  private final String amountKey = "amount";
  private final String descriptionKey = "description";
  private final String recurringTypeKey = "recurringType";
  private final String categoryKey = "category";

  @FXML private TextField expenseAmountField;
  @FXML private TextField expenseDescriptionField;

  @FXML private ComboBox<ExpenseCategory> categoryComboBox;

  @FXML private ComboBox<RecurringType> recurringIntervalComboBox;

  public AddExpenseDialog() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/addExpenseDialog.fxml"));
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
    this.setTitle("Add Expense");

    ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

    this.setResultConverter(
        dialogButton -> {
          if (dialogButton == submitButton) {
            if (assertAllFieldsValid()) {
              HashMap values = new HashMap();
              values.put(amountKey, getExpenseAmountField());
              values.put(descriptionKey, getExpenseDescriptionField());
              values.put(recurringTypeKey, getRecurringIntervalComboBox());
              values.put(categoryKey, getExpenseCategoryComboBox());
              return values;
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Please fill out all fields in dialog");
              alert.showAndWait();
            }
          }
          return null;
        });

    // adds enums to combo boxes
    recurringIntervalComboBox.getItems().addAll(RecurringType.values());
    categoryComboBox.getItems().addAll(ExpenseCategory.values());

    // force the field to be numeric only
    expenseAmountField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                expenseAmountField.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });

    // force the field to not start with space
    expenseDescriptionField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if ((oldValue.isEmpty() || oldValue.isBlank()) && newValue.matches(" ")) {
                expenseDescriptionField.clear();
              }
            });
  }

  public String getExpenseDescriptionField() {
    return expenseDescriptionField.getText();
  }

  public String getExpenseAmountField() {
    return expenseAmountField.getText();
  }

  public RecurringType getRecurringIntervalComboBox() {
    return recurringIntervalComboBox.getValue();
  }

  public ExpenseCategory getExpenseCategoryComboBox() {
    return categoryComboBox.getValue();
  }

  boolean assertAllFieldsValid() {
    return (!expenseDescriptionField.getText().isEmpty()
        && expenseAmountField.getText() != null
        && recurringIntervalComboBox.getValue() != null
        && categoryComboBox.getValue() != null);
  }
}
