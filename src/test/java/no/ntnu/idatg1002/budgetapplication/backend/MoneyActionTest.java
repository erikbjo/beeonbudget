package no.ntnu.idatg1002.budgetapplication.backend;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class MoneyActionTest {

  MoneyAction testAction = new MoneyAction(50, "Test description", Category.HOUSING, RecurringType.YEARLY) {};

  @Test
  void checkThatGetAmountReturnsCorrectAmount() {
    assertEquals(50, testAction.getAmount());
  }
  @Test
  void checkThatSetAmountSetsNewAmount() {
    testAction.setAmount(100);
    assertEquals(100, testAction.getAmount());
  }

  @Test
  void checkThatSetAmountRequiresAboveZero() {
    testAction.setAmount(-1);
    assertEquals(50, testAction.getAmount());
  }

  @Test
  void checkThatSetAmountRequiresAboveOrEqualToZero() {
    testAction.setAmount(0);
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
    testAction.setDescription(" ");
    assertEquals("Test description", testAction.getDescription());
  }

  @Test
  void checkThatSetDescriptionNeedsNotEmptyString() {
    testAction.setDescription("");
    assertEquals("Test description", testAction.getDescription());
  }
  @Test
  void checkThatGetCategoryReturnsCorrectCategory() {
    assertEquals(Category.HOUSING, testAction.getCategory());
  }
  @Test
  void checkThatSetCategorySetsCategory() {
    testAction.setCategory(Category.HEALTHCARE);
    assertEquals(Category.HEALTHCARE, testAction.getCategory());
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