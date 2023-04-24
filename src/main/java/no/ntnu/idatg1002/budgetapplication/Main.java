package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

public class Main {
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

    PrimaryView.mainApp(args);
  }
}
