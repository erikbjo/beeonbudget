package no.ntnu.idatg1002.budgetapplication.backend;

/** The type Income. */
public class Income extends MoneyAction {

  /**
   * Instantiates a new Income.
   *
   * @param amount the amount
   * @param description the description
   * @param category the category
   * @param type the type
   */
  public Income(int amount, String description, Category category, RecurringType type) {
    super(amount, description, category, type);
  }
}
