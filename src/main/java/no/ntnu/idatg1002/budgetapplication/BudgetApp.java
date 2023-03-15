package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;

/**
 * The main class of application.
 */
public class BudgetApp {

  public static void main(String[] args) {
    //Code here
    Account account = new Account("Simon", "s@com", "1234",
        SecurityQuestion.CAR_BRAND, "BMW");
    System.out.println(account.getAccountNumber());
    for (SecurityQuestion q : SecurityQuestion.values()) {
      System.out.println(q.getSecurityQuestionString());
    }
  }

}
