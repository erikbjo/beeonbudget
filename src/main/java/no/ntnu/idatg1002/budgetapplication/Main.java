package no.ntnu.idatg1002.budgetapplication;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Database;
import no.ntnu.idatg1002.budgetapplication.frontend.view.PrimaryView;

public class Main {

  public static void main(String[] args) {
    Account adminAccount =
        new Account(
            "Admin", "admin@bob.com", "8008", SecurityQuestion.FAVORITE_FOOD, "Klubb og duppe");
    Database.addAccount(adminAccount);
    Database.setCurrentAccount(adminAccount);
    Budget adminBudget = new Budget("testBudget");
    adminBudget.addBudgetExpenses(
        new Expense(100, "testdesc", RecurringType.DAILY, ExpenseCategory.FOOD));
    adminBudget.addBudgetIncome(
        new Income(500, "testdesc", RecurringType.DAILY, IncomeCategory.WAGE));
    Database.getCurrentAccount().addBudget(adminBudget);

    // for testing
    System.out.println("Budget size: " + adminAccount.getBudgets().size());
    System.out.println(
        "Selected savingsplan: " + Database.getCurrentAccount().getSelectedSavingsPlan());
    System.out.println("Selected budget: " + Database.getCurrentAccount().getSelectedBudget());

    PrimaryView.mainApp(args);
  }
}
