package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

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
    income = new Income(200, "sdvdvdvs", Category.HOUSING, RecurringType.NONRECURRING);
    expense = new Expense(300, "eseglerl", Category.HOUSING, RecurringType.NONRECURRING);
    budget.addBudgetIncome(income);
    budget.addBudgetExpenses(expense);
  }

  @Test
  void checkThatConstructorNeedsToHaveValidParameters() {
    Exception thrownBudgetNameError =
        assertThrows(IllegalArgumentException.class, () -> new Budget(" "));
    assertEquals("Budget name must not be empty or blank", thrownBudgetNameError.getMessage());
  }

  @Test
  void checkThatSetBudgetNameNeedsNotBlankString() {
    Exception thrown =
        assertThrows(IllegalArgumentException.class, () -> budget.setBudgetName(" "));
    assertEquals("Budget name must not be empty or blank", thrown.getMessage());
  }

  @Test
  void checkThatSetBudgetNameNeedsNotEmptyString() {
    Exception thrown = assertThrows(IllegalArgumentException.class, () -> budget.setBudgetName(""));
    assertEquals("Budget name must not be empty or blank", thrown.getMessage());
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
        new Income(200, "jbfgdfgui", Category.HOUSING, RecurringType.NONRECURRING));
    budget.addBudgetExpenses(
        new Expense(150, "JVhfyhkguug", Category.HEALTHCARE, RecurringType.MONTHLY));

    int netBalance = budget.getNetBalance();
    assertEquals(50, netBalance);
  }

  @AfterEach
  void tearDown() {}
}
