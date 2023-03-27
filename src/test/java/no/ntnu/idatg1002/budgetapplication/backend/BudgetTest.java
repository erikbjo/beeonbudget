package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BudgetTest {

  private Budget budget;
  private Income income;
  private Expense expense;

  @BeforeEach
  void setUp() {
    budget = new Budget("Test");
    income = new Income(200, "Test income 1", Category.HOUSING, RecurringType.NONRECURRING);
    expense = new Expense(300, "Test expense 1", Category.HOUSING, RecurringType.NONRECURRING);
    budget.addBudgetIncome(income);
    budget.addBudgetExpenses(expense);

    // Adding all categories to the budget
    for (Category cat : Category.values()) {
      budget.addCategory(cat);
    }
  }

  @Test
  void getNetBalanceSmallerThanZero() {
    int netBalance = budget.getNetBalance();

    assertTrue(netBalance < 0);
  }

  @Test
  void getNetBalanceEqualToFifty() {
    budget.getIncomeList().remove(income);
    budget.getExpenseList().remove(expense);
    budget.addBudgetIncome(
        new Income(200, "Test income 2", Category.HOUSING, RecurringType.NONRECURRING));
    budget.addBudgetExpenses(
        new Expense(150, "Test expense 2", Category.HEALTHCARE, RecurringType.MONTHLY));

    int netBalance = budget.getNetBalance();
    assertEquals(50, netBalance);
  }

  @Test
  void getCategoryListReturnsEnum() {
    assertTrue(budget.getCategoryList().containsAll(List.of(Category.values())));
  }

  @Test
  void setBudgetNameNeedsToHaveValidName() {
    budget.setBudgetName("Control name");
    budget.setBudgetName("");
    budget.setBudgetName(" ");
    assertEquals("Control name", budget.getBudgetName());
  }

  @AfterEach
  void tearDown() {}
}
