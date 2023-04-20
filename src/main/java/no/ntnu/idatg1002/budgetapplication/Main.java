package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.*;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.AccountDAO;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

public class Main {
  public static void main(String[] args) {
    try {
      Account adminAccount =
          new Account(
              "Admin", "admin@bob.com", "8008", SecurityQuestion.FAVORITE_FOOD, "Klubb og duppe");
      Budget adminBudget = new Budget("testBudget");
      adminBudget.addBudgetExpenses(
          new Expense(100, "testdesc", RecurringType.DAILY, ExpenseCategory.FOOD));
      adminBudget.addBudgetIncome(
          new Income(500, "testdesc", RecurringType.DAILY, IncomeCategory.WAGE));
      adminAccount.addBudget(adminBudget);

      AccountDAO.getInstance().addAccount(adminAccount);
      AccountDAO.getInstance().printAccounts();
    } catch (Exception e) {
      e.printStackTrace();
    }

    PrimaryView.mainApp(args);
  }
}
