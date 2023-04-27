package no.ntnu.idatg1002.budgetapplication.frontend.view;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** The view that is shown when the application launches. */
public class PrimaryView extends Application {

  /** Launches the application. */
  public static void mainApp(String[] args) {
    launch();
  }

  /**
   * Starts the application.
   *
   * @param primaryStage the primary stage
   * @throws Exception if there is an issue loading the FXML file
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/login.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    String css =
        Objects.requireNonNull(this.getClass().getResource("/cssfiles/primary.css"))
            .toExternalForm();
    scene.getStylesheets().add(css);
    primaryStage.setTitle("Bee on Budget");
    primaryStage
        .getIcons()
        .add(
            new Image(
                Objects.requireNonNull(getClass().getResource("/images/simpleLogoBoY.png"))
                    .openStream()));
    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.setMinHeight(500);
    primaryStage.setMinWidth(1000);
    primaryStage.show();
  }
}
