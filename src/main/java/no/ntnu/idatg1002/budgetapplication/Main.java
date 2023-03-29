package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

public class Main {

  public static void main(String[] args) {
    Account account = new Account("Admin", "admin@bob.com", "0000",
        SecurityQuestion.FAVORITE_FOOD, "Klubb og duppe");
    Database.addAccount(account);
    PrimaryView.mainApp(args);
  }

}
