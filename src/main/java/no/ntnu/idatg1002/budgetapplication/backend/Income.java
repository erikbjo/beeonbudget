package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents an income entry in the budget application. Inherits from the MoneyAction class. An
 * Income object includes an amount, a description, a recurring type, and an income category.
 *
 * @author Erik Bjørnsen, Simon Houmb, Eskil Alstad
 * @version 2.0
 */
@Entity
public class Income extends MoneyAction {
  @Id @GeneratedValue private Long id;
  private IncomeCategory incomeCategory;
  private LocalDate dateAdded;

  /**
   * Constructs an Income object with the specified amount, description, recurring type, income
   * category, and the date of the income.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the income
   * @param type the recurring type of the income
   * @param incomeCategory the income category associated with the income
   * @param dateAdded the date of the income.
   */
  public Income(
      int amount,
      String description,
      RecurringType type,
      IncomeCategory incomeCategory,
      LocalDate dateAdded) {
    super(amount, description, type);
    this.incomeCategory = incomeCategory;
    this.dateAdded = dateAdded;
  }

  /**
   * Constructs an Income object with the specified amount, description, recurring type and income
   * category. Sets the date added to current time.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the income
   * @param type the recurring type of the income
   * @param incomeCategory the income category associated with the income
   */
  public Income(int amount, String description, RecurringType type, IncomeCategory incomeCategory) {
    super(amount, description, type);
    this.incomeCategory = incomeCategory;
    this.dateAdded = LocalDate.now();
  }

  /** Default constructor for income */
  public Income() {}

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
  public void setIncomeCategory(IncomeCategory incomeCategory) throws IllegalArgumentException {
    if (incomeCategory == null) {
      throw new IllegalArgumentException();
    } else {
      this.incomeCategory = incomeCategory;
    }
  }

  /**
   * Returns the income category as a string.
   *
   * @return the income category as a string.
   */
  public String getIncomeCategoryString() {
    return this.incomeCategory.getIncomeCategoryLabel();
  }

  /**
   * Returns the date added.
   *
   * @return the date of the income.
   */
  public LocalDate getDateAdded() {
    return dateAdded;
  }

  /**
   * Returns the income as a string. Adds all parameters of the income to the string.
   *
   * @return the income formatted as a string.
   */
  public String getIncomeAsString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Amount: ").append(this.getAmount()).append(" kr").append("\n");
    stringBuilder.append("Description: ").append(this.getDescription()).append("\n");
    stringBuilder.append("Type: ").append(this.getRecurringType().getRecurringType()).append("\n");
    stringBuilder
        .append("Category: ")
        .append(this.incomeCategory.getIncomeCategoryLabel())
        .append("\n");
    stringBuilder
        .append("Income Date: ")
        .append(dateAdded.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    return stringBuilder.toString();
  }
}
