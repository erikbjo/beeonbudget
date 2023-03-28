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
    income = new Income(200, "Test income", Category.HOUSING, RecurringType.NONRECURRING);
    expense = new Expense(300, "Test expense", Category.FOOD, RecurringType.NONRECURRING);
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
  void testGetCategoriesContainsCategoriesFromMoneyActions() {
    assertTrue(budget.getCategoryList().contains(income.getCategory()));
    assertTrue(budget.getCategoryList().contains(expense.getCategory()));
  }

  @Test
  void assertAddToListMethodsWorks() {
    assertTrue(budget.getIncomeList().contains(income));
    assertTrue(budget.getExpenseList().contains(expense));
  }

  @Test
  void assertRemoveFromListMethodsWorks() {
    budget.removeBudgetIncome(income);
    budget.removeBudgetExpenses(expense);
    assertFalse(budget.getIncomeList().contains(income));
    assertFalse(budget.getExpenseList().contains(expense));
  }

  @Test
  void assertRemoveFromListAlsoRemovesCategory() {
    budget.removeBudgetIncome(income);
    budget.removeBudgetExpenses(expense);
    assertFalse(budget.getCategoryList().contains(income.getCategory()));
    assertFalse(budget.getCategoryList().contains(expense.getCategory()));
  }
  @Test
  void getNetBalanceSmallerThanZero() {
    int netBalance = budget.getNetBalance();

    assertTrue(netBalance < 0);
  }

  @Test
  void getNetBalanceEqualToFifty() {
    budget.removeBudgetIncome(income);
    budget.removeBudgetExpenses(expense);
    budget.addBudgetIncome(
        new Income(200, "Test income 2", Category.HOUSING, RecurringType.NONRECURRING));
    budget.addBudgetExpenses(
        new Expense(150, "Test expense 2", Category.HEALTHCARE, RecurringType.MONTHLY));

    int netBalance = budget.getNetBalance();
    assertEquals(50, netBalance);
  }


  @AfterEach
  void tearDown() {}
}
