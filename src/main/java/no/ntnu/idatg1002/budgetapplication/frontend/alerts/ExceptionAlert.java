package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class ExceptionAlert extends Alert {
  private ExceptionAlert(AlertType alertType) {
    super(alertType);
  }

  private ExceptionAlert(AlertType alertType, String s, ButtonType... buttonTypes) {
    super(alertType, s, buttonTypes);
  }

  public ExceptionAlert(Exception exception) {
    super(Alert.AlertType.WARNING);
    this.setTitle("Error");
    this.setHeaderText(null);
    this.setContentText(exception.getMessage());
    this.initModality(Modality.APPLICATION_MODAL);
  }
}
