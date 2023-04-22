package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class ConfirmationAlert extends Alert {
  public ConfirmationAlert(String title, String contentText) {
    super(AlertType.CONFIRMATION);
    this.initModality(Modality.APPLICATION_MODAL);
    this.setTitle(title);
    this.setContentText(contentText);
  }
}