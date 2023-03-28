package no.ntnu.idatg1002.budgetapplication.frontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.text.TabableView;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;

public class BudgetController implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent parent;

  @FXML private TableView<Budget> budgetView;
  @FXML private TableColumn<Budget, Expense> expenseColumn;
  @FXML private TableColumn<Budget, Income> incomeColumn;
  @FXML private  TableColumn<Budget, String> categoryColumn;


  public void switchToPrimaryFromBudget(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    expenseColumn.setCellValueFactory(new PropertyValueFactory<Budget, Expense>("expenses"));
    incomeColumn.setCellValueFactory(new PropertyValueFactory<Budget, Income>("income"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("category"));
    budgetView.getColumns().add(expenseColumn);
  }
}
