package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The type Income.
 *
 * @author Erik Bjørnsen
 * @version 2.0
 */
public class Income extends MoneyAction {

  /**
   * Instantiates a new Income.
   *
   * @param amount the amount
   * @param description the description
   * @param type the type
   */
  public Income(int amount, String description, RecurringType type, IncomeCategory incomeCategory) {
    super(amount, description, type);
  }
}
