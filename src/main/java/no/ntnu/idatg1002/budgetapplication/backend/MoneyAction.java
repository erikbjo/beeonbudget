package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents a money action in the budget application, serving as a superclass for Expense and
 * Income classes. MoneyAction instances store information about the amount, description, and
 * recurring-type of a financial transaction.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
public abstract class MoneyAction {
  private int amount;
  private String description;
  private RecurringType type;

  /**
   * Constructs a MoneyAction object with the specified amount, description, and recurring type.
   *
   * @param amount the monetary amount, must be non-negative
   * @param description a non-empty, non-blank description of the action
   * @param type the recurring type of the action
   * @throws IllegalArgumentException if the amount is negative or the description is empty or blank
   */
  protected MoneyAction(int amount, String description, RecurringType type)
      throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be non-negative.");
    }
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description must not be empty or blank.");
    }
    this.amount = amount;
    this.description = description;
    this.type = type;
  }

  /**
   * Returns the amount associated with this MoneyAction.
   *
   * @return the monetary amount of this action
   */
  public int getAmount() {
    return amount;
  }

  /**
   * Sets the amount for this MoneyAction.
   *
   * @param amount the new monetary amount, must be non-negative
   * @throws IllegalArgumentException if the amount is negative
   */
  public void setAmount(int amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be non-negative.");
    }
    this.amount = amount;
  }

  /**
   * Returns the description associated with this MoneyAction.
   *
   * @return the description of this action
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description for this MoneyAction.
   *
   * @param description a non-empty, non-blank description of the action
   * @throws IllegalArgumentException if the description is empty or blank
   */
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description must not be empty or blank.");
    }
    this.description = description;
  }

  /**
   * Returns the reoccurring type associated with this MoneyAction.
   *
   * @return the reoccurring type of this action
   */
  public RecurringType getRecurringType() {
    return type;
  }

  /**
   * Sets the reoccurring type associated with this MoneyAction.
   *
   * @param type the reocurring type of this action
   */
  public void setRecurringType(RecurringType type) {
    this.type = type;
  }
}
