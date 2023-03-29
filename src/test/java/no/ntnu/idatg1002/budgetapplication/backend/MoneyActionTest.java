package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoneyActionTest {
  MoneyAction testAction;
  Expense testExpense;

  @BeforeEach
  void setUp() {
    testAction = new Income(50, "Test description", RecurringType.YEARLY, IncomeCategory.PASSIVE_INCOME);
    testExpense = new Expense(50, "Test expense", RecurringType.DAILY, ExpenseCategory.HOUSING);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void checkThatConstructorNeedsToHaveValidParameters() {
    Exception thrownDescriptionError =
        assertThrows(
            IllegalArgumentException.class, () -> new Income(100, " ", RecurringType.YEARLY, IncomeCategory.PASSIVE_INCOME));
    assertEquals("Description must not be empty or blank.", thrownDescriptionError.getMessage());
  }

  @Test
  void checkThatGetAmountReturnsCorrectAmount() {
    assertEquals(50, testAction.getAmount());
  }

  @Test
  void checkThatSetAmountSetsNewAmount() {
    assertDoesNotThrow(() -> testAction.setAmount(0));
    assertEquals(0, testAction.getAmount());
  }

  @Test
  void checkThatSetAmountRequiresAboveZero() {
    Exception thrown = assertThrows(IllegalArgumentException.class, () -> testAction.setAmount(-1));
    assertEquals("Amount must be non-negative.", thrown.getMessage());
  }

  @Test
  void checkThatSetAmountRequiresAboveOrEqualToZero() {
    assertDoesNotThrow(() -> testAction.setAmount(0));
    assertEquals(0, testAction.getAmount());
  }

  @Test
  void checkThatGetDescriptionReturnsCorrectDescription() {
    assertEquals("Test description", testAction.getDescription());
  }

  @Test
  void checkThatSetDescriptionSetsNewDescription() {
    testAction.setDescription("New test description");
    assertEquals("New test description", testAction.getDescription());
  }

  @Test
  void checkThatSetDescriptionNeedsNotBlankString() {
    Exception thrown =
        assertThrows(IllegalArgumentException.class, () -> testAction.setDescription(" "));
    assertEquals("Description must not be empty or blank.", thrown.getMessage());
  }

  @Test
  void checkThatSetDescriptionNeedsNotEmptyString() {
    Exception thrown =
        assertThrows(IllegalArgumentException.class, () -> testAction.setDescription(""));
    assertEquals("Description must not be empty or blank.", thrown.getMessage());
  }

  @Test
  void checkThatGetCategoryReturnsCorrectCategory() {
    assertEquals(ExpenseCategory.HOUSING, testExpense.getCategory());
  }

  @Test
  void checkThatSetCategorySetsCategory() {
    testExpense.setCategory(ExpenseCategory.HEALTHCARE);
    assertEquals(ExpenseCategory.HEALTHCARE, testExpense.getCategory());
  }

  @Test
  void checkThatGetTypeReturnsCorrectType() {
    assertEquals(RecurringType.YEARLY, testAction.getType());
  }

  @Test
  void checkThatSetTypeSetsType() {
    testAction.setType(RecurringType.NONRECURRING);
    assertEquals(RecurringType.NONRECURRING, testAction.getType());
  }
}
