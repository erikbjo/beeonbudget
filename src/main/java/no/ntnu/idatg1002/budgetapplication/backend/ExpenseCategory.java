package no.ntnu.idatg1002.budgetapplication.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the categories for expenses in the budget application. The enum includes the
 * categories housing, transportation, food, utilities, clothing, healthcare, insurance, other, and
 * energy.
 *
 * @author Erik Bjørnsen, Simon Husås Houmb
 * @version 1.2
 */
public enum ExpenseCategory {
  /** Indicates expenses related to housing, such as rent or mortgage payments. */
  HOUSING("Housing"),
  /** Indicates expenses related to transportation, such as vehicle maintenance or public transit. */
  TRANSPORTATION("Transportation"),
  /** Indicates expenses related to food, such as groceries or dining out. */
  FOOD("Food"),
  /** Indicates expenses related to utilities, such as water, electricity, or gas. */
  UTILITIES("Utilities"),
  /** Indicates expenses related to savings */
  SAVINGS("Savings"),
  /** Indicates expenses related to recreational use */
  RECREATION_ENTERTAINMENT("Recreation & Entertainment"),
  /** Indicates expenses related to investment */
  INVESTMENT("Investment"),
  /** Indicates expenses related to clothing and personal items. */
  CLOTHING("Clothing"),
  /** Indicates expenses related to healthcare, such as medical or dental appointments. */
  HEALTHCARE("Healthcare"),
  /** Indicates expenses related to insurance, such as home, auto, or life insurance. */
  INSURANCE("Insurance"),
  /** Indicates expenses related to energy, such as electricity or gas bills. */
  ENERGY("Energy"),
  /** Indicates expenses that do not fit into other predefined categories. */
  OTHER("Other");

  private final String expenseCategoryString;

  ExpenseCategory(String expenseCategoryString) {
    this.expenseCategoryString = expenseCategoryString;
  }

  public String getExpenseCategoryString() {
    return expenseCategoryString;
  }

  public static List<String> stringValues() {
    ArrayList<String> stringValues = new ArrayList<>();
    for (ExpenseCategory category : ExpenseCategory.values()) {
      stringValues.add(category.expenseCategoryString);
    }
    return stringValues;
  }

  public static ExpenseCategory valueOfLabel(String label) {
    for (ExpenseCategory category : values()) {
      if (category.getExpenseCategoryString().equals(label)) {
        return category;
      }
    }
    return null;
  }
}
