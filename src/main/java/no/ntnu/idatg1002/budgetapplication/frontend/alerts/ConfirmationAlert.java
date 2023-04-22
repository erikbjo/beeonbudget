package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert extends Alert {
  public ConfirmationAlert(AlertType alertType) {
    super(alertType);
  }

  public ConfirmationAlert(AlertType alertType, String s, ButtonType... buttonTypes) {
    super(alertType, s, buttonTypes);
  }
}
