package no.ntnu.idatg1002.budgetapplication.frontend.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * General alert for confirmation's.
 *
 * @author Erik Bjørnsen
 */
public class ConfirmationAlert extends Alert {

  /**
   * Crates the confirmation alert with the given title and content text.
   *
   * @param title the title of the alert.
   * @param contentText the content text of the alert.
   */
  public ConfirmationAlert(String title, String contentText) {
    super(AlertType.CONFIRMATION);
    this.initModality(Modality.APPLICATION_MODAL);
    this.setTitle(title);
    this.setContentText(contentText);
  }
}
