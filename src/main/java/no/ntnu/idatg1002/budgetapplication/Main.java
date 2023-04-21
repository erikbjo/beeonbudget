package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
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
          new Income(500, "testdesc", RecurringType.DAILY, IncomeCategory.SALARY));

        AccountDAO.getInstance().addAccount(adminAccount);
        AccountDAO.getInstance().printAccounts();
      } catch (Exception e) {
        e.printStackTrace();
      }

   for (Account a : AccountDAO.getInstance().getAllAccounts()) {
      System.out.println("Account: " + (AccountDAO.getInstance().getAllAccounts().indexOf(a) + 1)
          + "/" + AccountDAO.getInstance().getAllAccounts().size());
      System.out.println("budgets: " + a.getBudgets().size());
      System.out.println("savingsPlans: " + a.getSavingsPlans().size() + "\n");
    }

      PrimaryView.mainApp(args);
    }
  }
