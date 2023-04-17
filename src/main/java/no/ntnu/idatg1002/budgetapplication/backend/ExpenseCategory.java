package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents the categories for expenses in the budget application. The enum includes the
 * categories housing, transportation, food, utilities, clothing, healthcare, insurance, other, and
 * energy.
 *
 * @author Erik Bjørnsen
 * @version 1.2
 */
public enum ExpenseCategory {
  /** Indicates expenses related to housing, such as rent or mortgage payments. */
  HOUSING,
  /**
   * Indicates expenses related to transportation, such as vehicle maintenance or public transit.
   */
  TRANSPORTATION,
  /** Indicates expenses related to food, such as groceries or dining out. */
  FOOD,
  /** Indicates expenses related to utilities, such as water, electricity, or gas. */
  UTILITIES,
  /** Indicates expenses related to clothing and personal items. */
  CLOTHING,
  /** Indicates expenses related to healthcare, such as medical or dental appointments. */
  HEALTHCARE,
  /** Indicates expenses related to insurance, such as home, auto, or life insurance. */
  INSURANCE,
  /** Indicates expenses that do not fit into other predefined categories. */
  OTHER,
  /** Indicates expenses related to energy, such as electricity or gas bills. */
  ENERGY;
}
