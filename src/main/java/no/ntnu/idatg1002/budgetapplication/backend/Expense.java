package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The type Expense.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
public class Expense extends MoneyAction {

  private Category category;

  /**
   * Instantiates a new Expense.
   *
   * @param amount the amount
   * @param description the description
   * @param type the type
   * @param category the category
   */
  public Expense(int amount, String description, RecurringType type, Category category) {
    super(amount, description, type);
    this.category = category;
  }

  /**
   * Gets category.
   *
   * @return the category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Sets category.
   *
   * @param category the category
   */
  public void setCategory(Category category) {
    this.category = category;
  }
}
