package no.ntnu.idatg1002.budgetapplication.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the recurring types of MoneyActions in the budget application. The enum includes
 * yearly, monthly, weekly, daily, and non-recurring types.
 *
 * @author Erik Bjørnsen
 * @version 1.2
 */
public enum RecurringType {
  /** Indicates a non-recurring transaction. */
  NONRECURRING("Non-Recurring"),
  /** Indicates a daily recurring transaction. */
  DAILY("Daily"),
  /** Indicates a weekly recurring transaction. */
  WEEKLY("Weekly"),
  /** Indicates a monthly recurring transaction. */
  MONTHLY("Monthly"),
  /** Indicates a yearly recurring transaction. */
  YEARLY("Yearly");

  private final String recurringTypeString;

  /**
   * Constructs a RecurringType with the specified string representation.
   *
   * @param recurringTypeString The string representation of the recurring type.
   */
  RecurringType(String recurringTypeString) {
    this.recurringTypeString = recurringTypeString;
  }

  /**
   * Returns the string representation of the recurring type.
   *
   * @return The string representation of the recurring type.
   */
  public String getRecurringType() {
    return recurringTypeString;
  }

  /**
   * Returns a list of string representations for all RecurringType values.
   *
   * @return A list of string representations for all RecurringType values.
   */
  public static List<String> labelValues() {
    ArrayList<String> labelValues = new ArrayList<>();
    for (RecurringType type : RecurringType.values()) {
      labelValues.add(type.recurringTypeString);
    }
    return labelValues;
  }

  /**
   * Returns the RecurringType value corresponding to the specified string label.
   *
   * @param label The string label to find the corresponding RecurringType value.
   * @return The RecurringType value corresponding to the label, or null if not found.
   */
  public static RecurringType valueOfLabel(String label) {
    for (RecurringType type : values()) {
      if (type.getRecurringType().equals(label)) {
        return type;
      }
    }
    return null;
  }
}
