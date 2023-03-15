package no.ntnu.idatg1002.budgetapplication.backend;

public class Expense extends MoneyAction {

  public Expense(int amount, String description, Category category, RecurringType type) {
    super(amount, description, category, type);
  }
}