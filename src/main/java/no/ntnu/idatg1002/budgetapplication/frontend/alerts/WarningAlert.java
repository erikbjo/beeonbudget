package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class WarningAlert extends Alert {
  public WarningAlert(String contentText) {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.setHeaderText(null);
    this.setContentText(contentText);
    this.initModality(Modality.APPLICATION_MODAL);
  }
}
