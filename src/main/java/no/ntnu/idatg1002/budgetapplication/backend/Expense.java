package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents an expense entry in the budget application. Inherits from the MoneyAction class. An
 * Expense object includes an amount, a description, a recurring type, and an expense category.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
public class Expense extends MoneyAction {

  private ExpenseCategory expenseCategory;

  /**
   * Constructs an Expense object with the specified amount, description, recurring type, and
   * expense category.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the expense
   * @param type the recurring type of the expense
   * @param expenseCategory the expense category associated with the expense
   */
  public Expense(
      int amount, String description, RecurringType type, ExpenseCategory expenseCategory) {
    super(amount, description, type);
    this.expenseCategory = expenseCategory;
  }

  /**
   * Returns the expense category associated with this Expense object.
   *
   * @return the expense category of this expense
   */
  public ExpenseCategory getCategory() {
    return expenseCategory;
  }

  /**
   * Sets the expense category for this Expense object.
   *
   * @param expenseCategory the new expense category to be associated with this expenses
   */
  public void setCategory(ExpenseCategory expenseCategory) {
    this.expenseCategory = expenseCategory;
  }
}
