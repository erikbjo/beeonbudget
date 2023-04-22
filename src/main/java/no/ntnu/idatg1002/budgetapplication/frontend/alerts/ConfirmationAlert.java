package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class ConfirmationAlert extends Alert {
  private ConfirmationAlert(AlertType alertType) {
    super(alertType);
  }

  private ConfirmationAlert(AlertType alertType, String s, ButtonType... buttonTypes) {
    super(alertType, s, buttonTypes);
  }

  public ConfirmationAlert(String title, String contentText) {
    super(AlertType.CONFIRMATION);
    this.initModality(Modality.APPLICATION_MODAL);
    this.setTitle(title);
    this.setContentText(contentText);
  }
}
