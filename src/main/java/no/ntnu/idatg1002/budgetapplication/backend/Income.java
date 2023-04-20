package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Represents an income entry in the budget application. Inherits from the MoneyAction class. An
 * Income object includes an amount, a description, a recurring type, and an income category.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
@Entity
public class Income extends MoneyAction {
  @Id
  @GeneratedValue
  private Long id;
  private IncomeCategory incomeCategory;

  /**
   * Constructs an Income object with the specified amount, description, recurring type, and income
   * category.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the income
   * @param type the recurring type of the income
   * @param incomeCategory the income category associated with the income
   */
  public Income(int amount, String description, RecurringType type, IncomeCategory incomeCategory) {
    super(amount, description, type);
    this.incomeCategory = incomeCategory;
  }

  public Income() {

  }

  /**
   * Returns the income category associated with this Income object.
   *
   * @return the income category of this income
   */
  public IncomeCategory getIncomeCategory() {
    return incomeCategory;
  }

  /**
   * Sets the income category for this Income object.
   *
   * @param incomeCategory the new income category to be associated with this income
   */
  public void setIncomeCategory(IncomeCategory incomeCategory) {
    this.incomeCategory = incomeCategory;
  }

  public String getIncomeAssString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Amount: ").append(this.getAmount()).append(".KR").append("\n");
    stringBuilder.append("Description: ").append(this.getDescription()).append("\n");
    stringBuilder.append("Type: ").append(this.getRecurringType()).append("\n");
    stringBuilder.append("ExpenseCategory: ").append(this.incomeCategory);
    return stringBuilder.toString();
  }
}
