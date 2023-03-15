package no.ntnu.idatg1002.budgetapplication.backend;

public class Income extends MoneyAction {

  public Income(int amount, String description, Category category, RecurringType type) {
    super(amount, description, category, type);
  }

}