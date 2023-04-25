package no.ntnu.idatg1002.budgetapplication;


import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

/**
 * Main class for the BudgetApplication.
 */
public class Main {

  /**
   * Main method that runs the application and JavaFx.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    for (Account a : AccountDAO.getInstance().getAll()) {
      System.out.println(
          "Account: "
              + (AccountDAO.getInstance().getAll().indexOf(a) + 1)
              + "/"
              + AccountDAO.getInstance().getAll().size());
      System.out.println("budgets: " + a.getBudgets().size());
      System.out.println("savingsPlans: " + a.getSavingsPlans().size() + "\n");
    }

    System.out.println(AccountDAO.getInstance().getAllAccountIds());

    PrimaryView.mainApp(args);
  }
}
