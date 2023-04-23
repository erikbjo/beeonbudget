package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class MoneyActionTest {
  private Income testIncome;
  private Income testIncomeWithDate;
  private Expense testExpense;
  private Expense testExpenseWithDate;

  @BeforeEach
  void setUp() {
    testIncome = new Income(50, "Test description", RecurringType.YEARLY, IncomeCategory.PASSIVE);
    testIncomeWithDate =
        new Income(
            50, "Test description", RecurringType.YEARLY, IncomeCategory.PASSIVE, LocalDate.now());
    testExpense = new Expense(50, "Test expense", RecurringType.DAILY, ExpenseCategory.HOUSING);
    testExpense =
        new Expense(
            50, "Test expense", RecurringType.DAILY, ExpenseCategory.HOUSING, LocalDate.now());
  }

  @AfterEach
  void tearDown() {}

  @Test
  void checkThatConstructorNeedsToHaveValidParameters() {
    Exception thrownDescriptionError =
        assertThrows(
            IllegalArgumentException.class,
            () -> new Income(100, " ", RecurringType.YEARLY, IncomeCategory.PASSIVE));
    assertEquals("Description must not be empty or blank.", thrownDescriptionError.getMessage());
  }

  @Test
  void checkThatGetAmountReturnsCorrectAmount() {
    assertEquals(50, testIncome.getAmount());
  }

  @Test
  void checkThatSetAmountSetsNewAmount() {
    assertDoesNotThrow(() -> testIncome.setAmount(0));
    assertEquals(0, testIncome.getAmount());
  }

  @Test
  void checkThatSetAmountRequiresAboveZero() {
    Exception thrown = assertThrows(IllegalArgumentException.class, () -> testIncome.setAmount(-1));
    assertEquals("Amount must be non-negative.", thrown.getMessage());
  }

  @Test
  void checkThatSetAmountRequiresAboveOrEqualToZero() {
    assertDoesNotThrow(() -> testIncome.setAmount(0));
    assertEquals(0, testIncome.getAmount());
  }

  @Test
  void checkThatGetDescriptionReturnsCorrectDescription() {
    assertEquals("Test description", testIncome.getDescription());
  }

  @Test
  void checkThatSetDescriptionSetsNewDescription() {
    testIncome.setDescription("New test description");
    assertEquals("New test description", testIncome.getDescription());
  }

  @Test
  void checkThatSetDescriptionNeedsNotBlankString() {
    Exception thrown =
        assertThrows(IllegalArgumentException.class, () -> testIncome.setDescription(" "));
    assertEquals("Description must not be empty or blank.", thrown.getMessage());
  }

  @Test
  void checkThatSetDescriptionNeedsNotEmptyString() {
    Exception thrown =
        assertThrows(IllegalArgumentException.class, () -> testIncome.setDescription(""));
    assertEquals("Description must not be empty or blank.", thrown.getMessage());
  }

  @Test
  void checkThatGetCategoryReturnsCorrectCategory() {
    assertEquals(ExpenseCategory.HOUSING, testExpense.getExpenseCategory());
  }

  @Test
  void checkThatSetCategorySetsCategory() {
    testExpense.setExpenseCategory(ExpenseCategory.HEALTHCARE);
    assertEquals(ExpenseCategory.HEALTHCARE, testExpense.getExpenseCategory());
  }

  @Test
  void checkThatGetTypeReturnsCorrectType() {
    assertEquals(RecurringType.YEARLY, testIncome.getRecurringType());
  }

  @Test
  void checkThatSetTypeSetsType() {
    testIncome.setRecurringType(RecurringType.NONRECURRING);
    assertEquals(RecurringType.NONRECURRING, testIncome.getRecurringType());
  }

  @Nested
  class categoryTest {
    @Test
    void setAndGetIncomeCategoryPositiveTest() {
      assertEquals(
          IncomeCategory.PASSIVE.getIncomeCategoryLabel(), testIncome.getIncomeCategoryString());

      testIncome.setIncomeCategory(IncomeCategory.OTHER);

      assertEquals(IncomeCategory.OTHER, testIncome.getIncomeCategory());
    }

    @Test
    void setIncomeCategoryNegativeTest() {
      assertThrows(IllegalArgumentException.class, () -> testIncome.setIncomeCategory(null));
    }

    @Test
    void setAndGetExpenseCategoryPositiveTest() {
      assertEquals(
          ExpenseCategory.HOUSING.getExpenseCategoryString(),
          testExpense.getExpenseCategoryString());

      testExpense.setExpenseCategory(ExpenseCategory.OTHER);

      assertEquals(ExpenseCategory.OTHER, testExpense.getExpenseCategory());
    }

    @Test
    void setExpenseCategoryNegativeTest() {
      assertThrows(IllegalArgumentException.class, () -> testExpense.setExpenseCategory(null));
    }
  }
}
