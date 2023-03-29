package no.ntnu.idatg1002.budgetapplication;

import javax.xml.crypto.Data;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

public class Main {

  public static void main(String[] args) {
    Account adminAccount = new Account("Admin", "admin@bob.com", "8008",
        SecurityQuestion.FAVORITE_FOOD, "Klubb og duppe");
    Database.addAccount(adminAccount);
    PrimaryView.mainApp(args);
  }

}
