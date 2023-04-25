package no.ntnu.idatg1002.budgetapplication.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the categories for incomes in the budget application. The enum includes the categories
 * work, rental income, profit income, capital gains income, passive income, wage, and investment.
 *
 * @author Emil Klegvård-Slåttsveen, Erik Bjørnsen, Simon Husås Houmb
 * @version 1.2
 */
public enum IncomeCategory {
  /** Indicates income earned through wages, such as salary or hourly pay. */
  SALARY("Salary"),
  /** Indicates income earned through a gift. */
  GIFT("Gift"),
  /** Indicates income earned through renting properties or assets. */
  RENTAL_PROFIT("Rental Profit"),
  /** Indicates income earned through profits from a business. */
  BUSINESS_PROFIT("Business Profit"),
  /** Indicates income earned through passive sources, such as dividends or royalties. */
  PASSIVE("Passive Income"),
  /** Indicates income earned through investments, such as stocks or bonds. */
  INVESTMENT_PROFIT("Investment Profit"),
  /** Indicates income that do not fit into other predefined categories. */
  OTHER("Other");

  private final String incomeCategoryLabel;

  /**
   * Constructs an IncomeCategory with the specified label.
   *
   * @param incomeCategoryLabel The label of the income category.
   */
  IncomeCategory(String incomeCategoryLabel) {
    this.incomeCategoryLabel = incomeCategoryLabel;
  }

  /**
   * Returns the label of the income category.
   *
   * @return The label of the income category.
   */
  public String getIncomeCategoryLabel() {
    return incomeCategoryLabel;
  }

  /**
   * Returns a list of labels for all IncomeCategory values.
   *
   * @return A list of labels for all IncomeCategory values.
   */
  public static List<String> labelValues() {
    ArrayList<String> labelValues = new ArrayList<>();
    for (IncomeCategory category : IncomeCategory.values()) {
      labelValues.add(category.incomeCategoryLabel);
    }
    return labelValues;
  }

  /**
   * Returns the IncomeCategory value corresponding to the specified label.
   *
   * @param label The label to find the corresponding IncomeCategory value.
   * @return The IncomeCategory value corresponding to the label, or null if not found.
   */
  public static IncomeCategory valueOfLabel(String label) {
    for (IncomeCategory category : values()) {
      if (category.getIncomeCategoryLabel().equals(label)) {
        return category;
      }
    }
    return null;
  }
}
