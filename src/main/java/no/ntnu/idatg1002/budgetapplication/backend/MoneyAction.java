package no.ntnu.idatg1002.budgetapplication.backend;

public abstract class MoneyAction {
  private int amount;
  private String description;
  private Category category;
  private RecurringType type;

  public MoneyAction (int amount, String description, Category category, RecurringType type) {
    this.amount = amount;
    this.description = description;
    this.category = category;
    this.type = type;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    if (amount >= 0) {
      this.amount = amount;
    }
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    if (!description.isEmpty() && !description.isBlank()) {
      this.description = description;
    }
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public RecurringType getType() {
    return type;
  }

  public void setType(RecurringType type) {
    this.type = type;
  }
}
