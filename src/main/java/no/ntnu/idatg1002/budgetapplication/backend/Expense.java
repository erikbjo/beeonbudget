package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The type Expense.
 *
 * @author Erik Bjørnsen
 */
public class Expense extends MoneyAction {

  /**
   * Instantiates a new Expense.
   *
   * @param amount the amount
   * @param description the description
   * @param category the category
   * @param type the type
   */
  public Expense(int amount, String description, Category category, RecurringType type) {
    super(amount, description, category, type);
  }
}
