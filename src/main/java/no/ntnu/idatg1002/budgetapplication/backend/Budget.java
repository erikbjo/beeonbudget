package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.PieChart;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.SessionAccount;

/**
 * Represents a budget, contains a list of expenses, a list of incomes, and a list of categories.
 *
 * @author Emil Klegvård-Slåttsveen, Erik Bjørnsen, Simon Husås Houmb, Eskil Alstad
 * @version 3.0
 */
@Entity
public class Budget {
  @Id @GeneratedValue private Long id;
  private String budgetName;
  private LocalDate startDate;
  private LocalDate endDate;
  private Period intervalLength;
  private int totalIncome;
  private int totalExpense;


  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private final List<Expense> expenseList = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private final List<Income> incomeList = new ArrayList<>();

  @ElementCollection
  @Enumerated(EnumType.ORDINAL)
  private final List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

  /**
   * Instantiates a new Budget.
   *
   * @param budgetName the budget name
   * @throws IllegalArgumentException "Budget name must not be empty or blank."
   * @throws IllegalArgumentException "Budget name must not exceed 24 characters."
   */
  public Budget(String budgetName, LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
    setBudgetName(budgetName);
    this.startDate = startDate;
    this.endDate = endDate;
    setIntervalLength();
  }

  public Budget(String budgetName) {
    setBudgetName(budgetName);
    this.startDate = LocalDate.now();
    this.endDate = LocalDate.now().plusDays(30);
    setIntervalLength();
  }

  public Budget() {}

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
   * @throws IllegalArgumentException "Budget name must not exceed 24 characters."
   * @throws IllegalArgumentException "Budget name is taken."
   */
  public void setBudgetName(String budgetName) throws IllegalArgumentException {
    if (budgetName == null || budgetName.trim().isEmpty()) {
      throw new IllegalArgumentException("Budget name must not be empty or blank.");
    }
    if (budgetName.length() > 24) {
      throw new IllegalArgumentException("Budget name must not exceed 24 characters.");
    }
    if (checkIfBudgetNameIsTaken(budgetName)) {
      throw new IllegalArgumentException("Budget name is taken.");
    }
    this.budgetName = budgetName;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getStartToEndString() {
    return String.format("%s - %s", getStartDate().format(DateTimeFormatter.ofLocalizedDate(
        FormatStyle.SHORT)), getEndDate().format(DateTimeFormatter.ofLocalizedDate(
        FormatStyle.SHORT)));
  }

  public Period getIntervalLength() {
    return this.intervalLength;
  }

  public void setIntervalLength() {
    this.intervalLength = this.startDate.until(this.endDate);
  }

  /**
   * Checks if the given budget's name is already taken by any other budget in the list. This method
   * iterates through the list of budgets and compares the names of each budget with the given
   * budget's name. If a match is found, the method returns true. If no match is found, the method
   * returns false.
   *
   * @param budgetName the Budget name that needs to be checked for uniqueness
   * @return true if the budget name is already taken, false otherwise
   */
  private boolean checkIfBudgetNameIsTaken(String budgetName) {
    boolean nameTaken = false;
    List<String> takenNames =
        SessionAccount.getInstance().getAccount().getBudgets().stream()
            .map(Budget::getBudgetName)
            .toList();
    for (String variableBudgetName : takenNames) {
      if (budgetName.equalsIgnoreCase(variableBudgetName)) {
        nameTaken = true;
      }
    }
    return nameTaken;
  }

  /**
   * Get the total income by adding up all the income objects in the incomeList.
   *
   * @return The total income of the user.
   */
  public int getTotalIncome() {
    totalIncome = 0;
    for (MoneyAction income : incomeList) {
      switch (income.getRecurringType()) {
        case NONRECURRING -> totalIncome += income.getAmount();
        case DAILY -> totalIncome += income.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getDays();
        case WEEKLY -> totalIncome += (income.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getDays()/7);
        case MONTHLY -> totalIncome += income.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getMonths();
        case YEARLY -> totalIncome += income.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getYears();
      }
    }
    return totalIncome;
  }

  /**
   * This function returns the total expense of all the expenses in the expense list.
   *
   * @return The total expense of the trip.
   */
  public int getTotalExpense() {
    totalExpense = 0;
    for (MoneyAction expense : expenseList) {
      switch (expense.getRecurringType()) {
        case NONRECURRING -> totalExpense += expense.getAmount();
        case DAILY -> totalExpense += expense.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getDays();
        case WEEKLY -> totalExpense += (expense.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getDays()/7);
        case MONTHLY -> totalExpense += expense.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getMonths();
        case YEARLY -> totalExpense += expense.getAmount() * SessionAccount.getInstance()
            .getAccount().getSelectedBudget().getIntervalLength().getYears();
      }
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
      if (!expenseCategoryList.contains(expense.getExpenseCategory())) {
        expenseCategoryList.add(expense.getExpenseCategory());
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

  /**
   * Generates pie chart data for expenses by category.
   *
   * @return a list of PieChart.Data objects representing expenses by category
   */
  public List<PieChart.Data> getPieChartExpenseData() {
    Map<String, Double> categories = new HashMap<>();
    for (Expense expense : this.getExpenseList()) {
      String category = expense.getExpenseCategory().getExpenseCategoryString();
      double amount = expense.getAmount();
      if (categories.containsKey(category)) {
        categories.put(category, categories.get(category) + amount);
      } else {
        categories.put(category, amount);
      }
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Double> entry : categories.entrySet()) {
      data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }
    return data;
  }

  /**
   * Generates pie chart data for incomes by category.
   *
   * @return a list of PieChart.Data objects representing incomes by category
   */
  public List<PieChart.Data> getPieChartIncomeData() {
    Map<String, Double> categories = new HashMap<>();
    for (Income income : this.getIncomeList()) {
      String category = income.getIncomeCategory().getIncomeCategoryLabel();
      double amount = income.getAmount();
      if (categories.containsKey(category)) {
        categories.put(category, categories.get(category) + amount);
      } else {
        categories.put(category, amount);
      }
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Double> entry : categories.entrySet()) {
      data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }
    return data;
  }

  /**
   * Generates pie chart data for total income and outcome.
   *
   * @return a list of PieChart.Data objects representing the total income and outcome
   */
  public List<PieChart.Data> getTotalIncomeAndOutCome() {
    Map<String, Integer> incomeOrExpense = new HashMap<>();
    for (Income income : this.getIncomeList()) {
      String incomeString = "Income";
      incomeOrExpense.put(incomeString, incomeOrExpense.getOrDefault(incomeString, 0) + getTotalIncome());
    }
    for (Expense expense : this.getExpenseList()) {
      String expenseString = "Expense";
      incomeOrExpense.put(expenseString, incomeOrExpense.getOrDefault(expenseString, 0) + getTotalExpense());
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : incomeOrExpense.entrySet()) {
      data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }
    return data;
  }

  public StringBuilder getBudgetAssString() {
    StringBuilder sb = new StringBuilder();
    sb.append(budgetName).append("\n");
    sb.append(getStartDate()).append("\n");
    sb.append(getEndDate());
    return sb;
  }
}
