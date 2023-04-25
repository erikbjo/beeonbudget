package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * General alert for warnings.
 *
 * @author Erik Bjørnsen
 */
public class WarningAlert extends Alert {
  /**
   * Creates an alert with the type warning. Uses the parameters to fill out the information in the
   * alert.
   *
   * @param contentText the content text of the alert
   */
  public WarningAlert(String contentText) {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.setContentText(contentText);
    this.initModality(Modality.APPLICATION_MODAL);
  }

  /**
   * Creates an alert with the type warning. Uses the parameters to fill out the information in the
   * alert.
   *
   * @param contentText the content text of the alert.
   * @param headerText the header text of the alert.
   */
  public WarningAlert(String contentText, String headerText) {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.setHeaderText(headerText);
    this.setContentText(contentText);
    this.initModality(Modality.APPLICATION_MODAL);
  }

  /** Standard constructor with no parameters, can be edited afterward. */
  public WarningAlert() {
    super(AlertType.WARNING);
    this.setTitle("Error");
    this.initModality(Modality.APPLICATION_MODAL);
  }
}
