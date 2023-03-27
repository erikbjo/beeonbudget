package no.ntnu.idatg1002.budgetapplication.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PrimaryView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.getIcons().add(new Image(
        getClass().getResource("/images/simpleLogoBoY.png").openStream()));
    //primaryStage.getIcons().add(image);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void mainApp(String[] args) {
    launch();
  }
}
