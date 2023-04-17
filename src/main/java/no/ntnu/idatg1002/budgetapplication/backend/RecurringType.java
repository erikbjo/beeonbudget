package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents the recurring types of MoneyActions in the budget application. The enum includes
 * yearly, monthly, weekly, daily, and non-recurring types.
 *
 * @author Erik Bjørnsen
 * @version 1.2
 */
public enum RecurringType {
  /** Indicates a yearly recurring transaction. */
  YEARLY,
  /** Indicates a monthly recurring transaction. */
  MONTHLY,
  /** Indicates a weekly recurring transaction. */
  WEEKLY,
  /** Indicates a daily recurring transaction. */
  DAILY,
  /** Indicates a non-recurring transaction. */
  NONRECURRING
}
