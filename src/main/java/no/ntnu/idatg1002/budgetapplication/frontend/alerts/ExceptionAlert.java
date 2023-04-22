package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class ExceptionAlert extends Alert {
  public ExceptionAlert(Exception exception) {
    super(Alert.AlertType.WARNING);
    this.setTitle("Error");
    this.setHeaderText(null);
    this.setContentText(exception.getMessage());
    this.initModality(Modality.APPLICATION_MODAL);
  }
}
