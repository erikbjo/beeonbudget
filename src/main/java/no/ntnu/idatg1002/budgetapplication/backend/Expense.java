package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The type Expense.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
public class Expense extends MoneyAction {

  private ExpenseCategory expenseCategory;

  /**
   * Instantiates a new Expense.
   *
   * @param expenseCategory the expenseCategory
   * @param amount the amount
   * @param description the description
   * @param type the type
   */
  public Expense(
      int amount, String description, RecurringType type, ExpenseCategory expenseCategory) {
    super(amount, description, type);
    this.expenseCategory = expenseCategory;
  }

  /**
   * Gets expenseCategory.
   *
   * @return the expenseCategory
   */
  public ExpenseCategory getCategory() {
    return expenseCategory;
  }

  /**
   * Sets expenseCategory.
   *
   * @param expenseCategory the expenseCategory
   */
  public void setCategory(ExpenseCategory expenseCategory) {
    this.expenseCategory = expenseCategory;
  }

  @Override
  public String toString() {
    return super.toString() + ", expenseCategory:" + this.getCategory();
  }
}
