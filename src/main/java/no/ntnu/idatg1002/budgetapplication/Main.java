package no.ntnu.idatg1002.budgetapplication;

import javax.xml.crypto.Data;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
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
    adminBudget.addBudgetExpenses(new Expense(100, "testdesc", RecurringType.DAILY, Category.FOOD));
    adminBudget.addBudgetIncome(new Income(500, "testdesc", RecurringType.NONRECURRING));
    Database.getCurrentAccount().addBudget(adminBudget);
    System.out.println(adminAccount.getBudgets().size());
    System.out.println(Database.getCurrentAccount().getSelectedSavingsPlan());
    System.out.println(Database.getCurrentAccount().getSelectedBudget());
    PrimaryView.mainApp(args);
  }
}
