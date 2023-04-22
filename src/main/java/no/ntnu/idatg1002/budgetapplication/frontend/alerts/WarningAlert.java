package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class WarningAlert extends Alert {
  public WarningAlert(String contentText) {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.setContentText(contentText);
    this.initModality(Modality.APPLICATION_MODAL);
  }

  public WarningAlert(String contentText, String headerText) {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.setHeaderText(headerText);
    this.setContentText(contentText);
    this.initModality(Modality.APPLICATION_MODAL);
  }

  public WarningAlert() {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.initModality(Modality.APPLICATION_MODAL);
  }
}
