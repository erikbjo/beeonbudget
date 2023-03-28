package no.ntnu.idatg1002.budgetapplication.frontend;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class BudgetView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    /**
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
     */
  }

  public static void budgetView(String[] args) {
    launch();
  }
}
