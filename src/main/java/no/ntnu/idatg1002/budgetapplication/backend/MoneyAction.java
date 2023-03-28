package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The type Money action. Superclass of Expense and Income.

 * @author Erik Bjørnsen
 */
public abstract class MoneyAction {
  private int amount;
  private String description;
  private Category category;
  private RecurringType type;

  /**
   * Instantiates a new Money action. Amount needs to be greater than zero and description needs
   * to not be empty or blank.
   *
   * @param amount the amount
   * @param description the description
   * @param category the category
   * @param type the type
   */
  public MoneyAction(int amount, String description, Category category, RecurringType type) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be non-negative");
    }
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description must not be empty or blank");
    }
    this.amount = amount;
    this.description = description;
    this.category = category;
    this.type = type;
  }

  /**
   * Gets amount.
   *
   * @return the amount
   */
  public int getAmount() {
    return amount;
  }

  /**
   * Sets amount. Amount must be above or equal to zero.
   *
   * @param amount the amount
   */
  public void setAmount(int amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be non-negative");
    }
    this.amount = amount;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description. Description cant be empty or blank.
   *
   * @param description the description
   */
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description must not be empty or blank");
    }
    this.description = description;
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

  /**
   * Gets type.
   *
   * @return the type
   */
  public RecurringType getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(RecurringType type) {
    this.type = type;
  }

}
