package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;

/**
 * The main class of application.
 */
public class BudgetApp {

  public static void main(String[] args) {
    //Code here
    Account account1 = new Account("Simon", "s@com", "1234",
        SecurityQuestion.CAR_BRAND, "BMW");
    Account account2 = new Account("Emil", "emilgmail.com", "12341",
        SecurityQuestion.FAVORITE_FOOD, "Pizza");
    Database.addAccount(account1);
    Database.addAccount(account2);

    for (Account a : Database.getAccounts().values()) {
      System.out.println(a);
    }
  }

}
