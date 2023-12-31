package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents an expense entry in the budget application. Inherits from the MoneyAction class. An
 * Expense object includes an amount, a description, a recurring type, and an expense category.
 *
 * @author Erik Bjørnsen, Simon Husås Houmb
 * @version 2.0
 */
@Entity
public class Expense extends MoneyAction {
  @Id @GeneratedValue private Long id;

  private ExpenseCategory expenseCategory;
  private LocalDate dateAdded;

  /**
   * Constructs an Expense object with the specified amount, description, recurring type, expense
   * category, and date of the expense.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the expense
   * @param type the recurring type of the expense
   * @param expenseCategory the expense category associated with the expense
   * @param dateAdded the date of the expense.
   */
  public Expense(
      int amount,
      String description,
      RecurringType type,
      ExpenseCategory expenseCategory,
      LocalDate dateAdded) {
    super(amount, description, type);
    this.expenseCategory = expenseCategory;
    this.dateAdded = dateAdded;
  }

  /**
   * Constructs an Expense object with the specified amount, description, recurring type, and
   * expense category. Sets the date added to current time.
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
    this.dateAdded = LocalDate.now();
  }

  /** Default constructor for expense. */
  public Expense() {}

  /**
   * Returns the expense category associated with this Expense object.
   *
   * @return the expense category of this expense
   */
  public ExpenseCategory getExpenseCategory() {
    return expenseCategory;
  }

  /**
   * Sets the expense category for this Expense object.
   *
   * @param expenseCategory the new expense category to be associated with this expenses
   * @throws IllegalArgumentException if the expense category is null
   */
  public void setExpenseCategory(ExpenseCategory expenseCategory) throws IllegalArgumentException {
    if (expenseCategory == null) {
      throw new IllegalArgumentException();
    } else {
      this.expenseCategory = expenseCategory;
    }
  }

  /**
   * Return the expenseCategory as a string.
   *
   * @return the expenseCategory as a string.
   */
  public String getExpenseCategoryString() {
    return this.expenseCategory.getExpenseCategoryString();
  }

  /**
   * Returns the date of the expense.
   *
   * @return the date of the expense.
   */
  public LocalDate getDateAdded() {
    return dateAdded;
  }

  /**
   * Returns the expense as a string. Adds all parameters of the expense to the string.
   *
   * @return the expense formatted as a string.
   */
  public String getExpenseAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Amount: ").append(this.getAmount()).append(" kr").append("\n");
    sb.append("Description: ").append(this.getDescription()).append("\n");
    sb.append("Type: ").append(this.getRecurringType().getRecurringType()).append("\n");
    sb.append("Category: ")
        .append(this.getExpenseCategory().getExpenseCategoryString())
        .append("\n");
    sb.append("Expense Date: ")
        .append(dateAdded.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    return sb.toString();
  }
}
