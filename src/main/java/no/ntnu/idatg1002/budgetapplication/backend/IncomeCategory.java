package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents the categories for incomes in the budget application. The enum includes the categories
 * work, rental income, profit income, capital gains income, passive income, wage, and investment.
 *
 * @author Emil Klegvård-Slåttsveen, Erik Bjørnsen
 * @version 1.2
 */
public enum IncomeCategory {
  /** Indicates income earned through work. */
  WORK,
  /** Indicates income earned through renting properties or assets. */
  RENTAL_INCOME,
  /** Indicates income earned through profits from a business. */
  PROFIT_INCOME,
  /** Indicates income earned through capital gains, such as the sale of investments or assets. */
  CAPITAL_GAINS_INCOME,
  /** Indicates income earned through passive sources, such as dividends or royalties. */
  PASSIVE_INCOME,
  /** Indicates income earned through wages, such as salary or hourly pay. */
  WAGE,
  /** Indicates income earned through investments, such as stocks or bonds. */
  INVESTMENT
}
