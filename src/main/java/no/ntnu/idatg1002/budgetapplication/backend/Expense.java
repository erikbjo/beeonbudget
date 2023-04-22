package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.text.DateFormat;
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
  @Id
  @GeneratedValue
  private Long id;

  private ExpenseCategory expenseCategory;
  private LocalDate dateAdded;

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
      int amount, String description, RecurringType type, ExpenseCategory expenseCategory,
      LocalDate dateAdded) {
    super(amount, description, type);
    this.expenseCategory = expenseCategory;
    this.dateAdded = dateAdded;
  }

  public Expense(
      int amount, String description, RecurringType type, ExpenseCategory expenseCategory) {
    super(amount, description, type);
    this.expenseCategory = expenseCategory;
    this.dateAdded = LocalDate.now();
  }

  public Expense() {

  }

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
   */
  public void setExpenseCategory(ExpenseCategory expenseCategory) {
    this.expenseCategory = expenseCategory;
  }
  public String getExpenseCategoryString() {
    return this.expenseCategory.getExpenseCategoryString();
  }

  public String getExpenseAssString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Amount: ").append(this.getAmount()).append(" kr").append("\n");
    sb.append("Description: ").append(this.getDescription()).append("\n");
    sb.append("Type: ").append(this.getRecurringType()).append("\n");
    sb.append("Category: ").append(this.expenseCategory.getExpenseCategoryString()).append("\n");
    sb.append("Date Added: ").append(dateAdded.format(DateTimeFormatter.ofLocalizedDate(
        FormatStyle.SHORT)));
    return sb.toString();
  }
}
