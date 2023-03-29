package no.ntnu.idatg1002.budgetapplication.frontend.view;

import java.awt.Label;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

public class PrimaryView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/primary.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    String css = this.getClass().getResource("/cssfiles/primary.css").toExternalForm();
    scene.getStylesheets().add(css);
    primaryStage.setTitle("Bee on Budget");
    primaryStage.getIcons().add(new Image(
        getClass().getResource("/images/simpleLogoBoY.png").openStream()));
    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  public static void mainApp(String[] args) {
    launch();
  }
}
