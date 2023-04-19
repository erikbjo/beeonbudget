package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart;

/**
 * Represents a budget, contains a list of expenses, a list of incomes, and a list of categories.
 *
 * @author Emil Klegvård-Slåttsveen, Erik Bjørnsen, Simon Husås Houmb
 * @version 3.0 (2023-03-28)
 */
@Entity
public class Budget {
  @Id
  @GeneratedValue
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private final List<Expense> expenseList = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private final List<Income> incomeList = new ArrayList<>();
  @ElementCollection
  @Enumerated(EnumType.ORDINAL)
  private final List<ExpenseCategory> expenseCategoryList = new ArrayList<>();
  private String budgetName;

  /**
   * Instantiates a new Budget.
   *
   * @param budgetName the budget name
   * @throws IllegalArgumentException "Budget name must not be empty or blank."
   */
  public Budget(String budgetName) throws IllegalArgumentException {
    if (budgetName == null || budgetName.trim().isEmpty()) {
      throw new IllegalArgumentException("Budget name must not be empty or blank.");
    }
    this.budgetName = budgetName;
  }

  public Budget() {

  }

  /**
   * This function returns the name of the budget.
   *
   * @return The budget name.
   */
  public String getBudgetName() {
    return budgetName;
  }

  /**
   * This function sets the budget name.
   *
   * @param budgetName The name of the budget you want to create.
   * @throws IllegalArgumentException "Budget name must not be empty or blank."
   */
  public void setBudgetName(String budgetName) throws IllegalArgumentException {
    if (budgetName == null || budgetName.trim().isEmpty()) {
      throw new IllegalArgumentException("Budget name must not be empty or blank.");
    }
    this.budgetName = budgetName;
  }

  /**
   * Get the total income by adding up all the income objects in the incomeList.
   *
   * @return The total income of the user.
   */
  public int getTotalIncome() {
    int totalIncome = 0;
    for (MoneyAction income : incomeList) {
      totalIncome += income.getAmount();
    }
    return totalIncome;
  }

  /**
   * This function returns the total expense of all the expenses in the expense list.
   *
   * @return The total expense of the trip.
   */
  public int getTotalExpense() {
    int totalExpense = 0;
    for (MoneyAction expense : expenseList) {
      totalExpense += expense.getAmount();
    }
    return totalExpense;
  }

  /**
   * This function returns the difference between the total income and the total expense.
   *
   * @return The difference between the total income and the total expense.
   */
  public int getNetBalance() {
    return getTotalIncome() - getTotalExpense();
  }

  /**
   * This function updates the category list. It iterates through the expenses and incomes currently
   * in the budget, and adds any category from them that is not currently in the budget, to the
   * budget.
   */
  private void updateCategoryList() {
    expenseCategoryList.clear();
    for (Expense expense : expenseList) {
      if (!expenseCategoryList.contains(expense.getCategory())) {
        expenseCategoryList.add(expense.getCategory());
      }
    }
  }

  /**
   * This function adds an expense to the expense list.
   *
   * @param expense The expense object that you want to add to the list.
   */
  public void addBudgetExpenses(Expense expense) {
    expenseList.add(expense);
    updateCategoryList();
  }

  /**
   * This function adds an income to the incomeList.
   *
   * @param income The income object to be added to the list.
   */
  public void addBudgetIncome(Income income) {
    incomeList.add(income);
    updateCategoryList();
  }

  /**
   * This function removes an expense from the expense list.
   *
   * @param expense The expense object that you want to remove from the list.
   * @throws IndexOutOfBoundsException "There is no such expense in the budget."
   */
  public void removeBudgetExpenses(Expense expense) throws IndexOutOfBoundsException {
    if (expenseList.contains(expense)) {
      expenseList.remove(expense);
      updateCategoryList();
    } else {
      throw new IndexOutOfBoundsException("There is no such expense in the budget.");
    }
  }

  /**
   * This function removes an income from the incomeList.
   *
   * @param income The income object to be removed from the list.
   * @throws IndexOutOfBoundsException "There is no such income in the budget"
   */
  public void removeBudgetIncome(Income income) throws IndexOutOfBoundsException {
    if (incomeList.contains(income)) {
      incomeList.remove(income);
      updateCategoryList();
    } else {
      throw new IndexOutOfBoundsException("There is no such income in the budget");
    }
  }

  /**
   * > This function returns the incomeList.
   *
   * @return A list of income objects.
   */
  public List<Income> getIncomeList() {
    return incomeList;
  }

  /**
   * This function returns the expenseList.
   *
   * @return A list of expenses.
   */
  public List<Expense> getExpenseList() {
    return expenseList;
  }

  /**
   * > This function returns a list of categories.
   *
   * @return A list of Enum objects.
   */
  public List<ExpenseCategory> getCategoryList() {
    return expenseCategoryList;
  }
}
