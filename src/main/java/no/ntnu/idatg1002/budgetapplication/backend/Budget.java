package no.ntnu.idatg1002.budgetapplication.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * Represents a budget, contains a list of expenses, a list of incomes, and a list of categories.
 *
 * @author Emil Klegvård-Slåttsveen, Erik Bjørnsen
 * @version 3.0 (2023-03-28)
 */
public class Budget {
  private final List<Expense> expenseList;
  private final List<Income> incomeList;
  private final List<ExpenseCategory> expenseCategoryList;
  private String budgetName;
  private Boolean categoryExists;

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
    incomeList = new ArrayList<>();
    expenseList = new ArrayList<>();
    expenseCategoryList = new ArrayList<>();
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

  public List<PieChart.Data> getPieChartExpenseData() {
    ArrayList<PieChart.Data> data = new ArrayList<>();
    for (Expense expense : this.getExpenseList()) {
      //data.add(new Data(expense.getCategory().toString(), expense.getAmount()));
      for (PieChart.Data dataPie : data) {
        if (dataPie.getName().equals(expense.getCategory().toString())) {
          // Update the existing data with the new amount
          dataPie.setPieValue(dataPie.getPieValue() + expense.getAmount());
        } else {
          data.add(new PieChart.Data(expense.getCategory().toString(), expense.getAmount()));
        }
      }
    }
    return data;
  }
  public List<PieChart.Data> getPieChartExpenseDataTest() {
    Map<String, Double> categoryAmounts = new HashMap<>();
    for (Expense expense : this.getExpenseList()) {
      String category = expense.getCategory().toString();
      double amount = expense.getAmount();
      if (categoryAmounts.containsKey(category)) {
        categoryAmounts.put(category, categoryAmounts.get(category) + amount);
      } else {
        categoryAmounts.put(category, amount);
      }
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Double> entry : categoryAmounts.entrySet()) {
      data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }
    return data;
  }

  public List<PieChart.Data> getPieChartIncomeData() {
    ArrayList<PieChart.Data> data = new ArrayList<>();
    for (Income income : this.getIncomeList()) {
      data.add(new PieChart.Data(income.getIncomeCategory().toString(), income.getAmount()));
    }
    return data;
  }
}
