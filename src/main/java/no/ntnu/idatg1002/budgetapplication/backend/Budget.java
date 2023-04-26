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
import java.time.temporal.ChronoUnit;
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
 * @version 3.1
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
   * Constructor for Budget with a specified name, start date, and end date.
   *
   * @param budgetName the name of the budget.
   * @param startDate the start date of the budget.
   * @param endDate the end date of the budget.
   * @throws IllegalArgumentException if the budget name is invalid or taken. Or if the interval
   *     length is negative.
   */
  public Budget(String budgetName, LocalDate startDate, LocalDate endDate)
      throws IllegalArgumentException {
    setBudgetName(budgetName);
    this.startDate = startDate;
    this.endDate = endDate;
    setIntervalLength();
  }

  /**
   * Constructor for Budget with only a name, creates a budget with creation date as start date, and
   * creation date + 30 days as end date.
   *
   * @param budgetName the name of the budget.
   * @throws IllegalArgumentException if the budget name is invalid or taken.
   */
  public Budget(String budgetName) {
    setBudgetName(budgetName);
    this.startDate = LocalDate.now();
    this.endDate = LocalDate.now().plusDays(30);
    setIntervalLength();
  }

  /** Default constructor for Budget. */
  public Budget() {}

  /**
   * Returns the name of the budget.
   *
   * @return the name of the budget
   */
  public String getBudgetName() {
    return budgetName;
  }

  /**
   * Sets the name of the budget.
   *
   * @param budgetName the name of the budget
   * @throws IllegalArgumentException if the budget name is invalid or taken
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

  /**
   * Returns the start date of the budget.
   *
   * @return the start date of the budget
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date of the budget.
   *
   * @param startDate the start date of the budget
   * @throws IllegalArgumentException if the start date is after the end date
   */
  public void setStartDate(LocalDate startDate) {
    if (startDate.isAfter(this.endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    this.startDate = startDate;
  }

  /**
   * Returns the end date of the budget.
   *
   * @return the end date of the budget
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date of the budget.
   *
   * @param endDate the end date of the budget
   * @throws IllegalArgumentException if the end date is before the start date
   */
  public void setEndDate(LocalDate endDate) {
    if (endDate.isBefore(this.startDate)) {
      throw new IllegalArgumentException("End date cannot be before start date");
    }
    this.endDate = endDate;
  }

  /**
   * Returns a formatted string containing the start and end dates of the budget.
   *
   * @return a formatted string containing the start and end dates
   */
  public String getStartToEndString() {
    return String.format(
        "%s - %s",
        getStartDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
        getEndDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
  }

  /**
   * Returns the interval length of the budget.
   *
   * @return the interval length of the budget
   */
  public Period getIntervalLength() {
    return this.intervalLength;
  }

  /**
   * Sets the interval length of the budget.
   *
   * @throws IllegalArgumentException if the start date is after the end date
   */
  public void setIntervalLength() {
    if (this.startDate.isAfter(this.endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    this.intervalLength = this.startDate.until(this.endDate);
  }

  /**
   * Checks if the given budget name is already taken by any existing budgets.
   *
   * @param budgetName the budget name to check
   * @return true if the budget name is already taken, false otherwise
   */
  private boolean checkIfBudgetNameIsTaken(String budgetName) {
    boolean nameTaken = false;
    if (!SessionAccount.getInstance().getAccount().getBudgets().isEmpty()) {
      List<String> takenNames =
          SessionAccount.getInstance().getAccount().getBudgets().stream()
              .map(Budget::getBudgetName)
              .toList();
      for (String variableBudgetName : takenNames) {
        if (budgetName.equalsIgnoreCase(variableBudgetName)) {
          nameTaken = true;
        }
      }
    }
    return nameTaken;
  }

  /**
   * Calculates the total expense of a given expense, considering its recurring type and the
   * budget's start and end dates.
   *
   * @param expense the expense to calculate the total expense for
   * @return the calculated total expense
   */
  private int calculateTotalExpense(Expense expense) throws IllegalArgumentException {
    totalExpense = expense.getAmount();
    switch (expense.getRecurringType()) {
      case NONRECURRING -> totalExpense = expense.getAmount();
      case DAILY -> {
        if (ChronoUnit.DAYS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalExpense *=
              ChronoUnit.DAYS.between(
                  expense.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalExpense = expense.getAmount();
        }
      }
      case WEEKLY -> {
        if (ChronoUnit.WEEKS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalExpense *=
              ChronoUnit.WEEKS.between(
                  expense.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalExpense = expense.getAmount();
        }
      }
      case MONTHLY -> {
        if (ChronoUnit.MONTHS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalExpense *=
              ChronoUnit.MONTHS.between(
                  expense.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalExpense = expense.getAmount();
        }
      }
      case YEARLY -> {
        if (ChronoUnit.YEARS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalExpense *=
              ChronoUnit.YEARS.between(
                  expense.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalExpense = expense.getAmount();
        }
      }
      default -> throw new IllegalArgumentException("No recurring type found.");
    }
    return totalExpense;
  }

  /**
   * Calculates the total income of a given income, considering its recurring type and the budget's
   * start and end dates.
   *
   * @param income the income to calculate the total income for
   * @return the calculated total income
   */
  private int calculateTotalIncome(Income income) throws IllegalArgumentException {
    totalIncome = income.getAmount();
    switch (income.getRecurringType()) {
      case NONRECURRING -> totalIncome = income.getAmount();
      case DAILY -> {
        if (ChronoUnit.DAYS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalIncome *=
              ChronoUnit.DAYS.between(
                  income.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalIncome = income.getAmount();
        }
      }
      case WEEKLY -> {
        if (ChronoUnit.WEEKS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalIncome *=
              ChronoUnit.WEEKS.between(
                  income.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalIncome = income.getAmount();
        }
      }
      case MONTHLY -> {
        if (ChronoUnit.MONTHS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalIncome *=
              ChronoUnit.MONTHS.between(
                  income.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalIncome = income.getAmount();
        }
      }
      case YEARLY -> {
        if (ChronoUnit.YEARS.between(
                SessionAccount.getInstance().getAccount().getSelectedBudget().startDate,
                SessionAccount.getInstance().getAccount().getSelectedBudget().endDate)
            >= 1) {
          totalIncome *=
              ChronoUnit.YEARS.between(
                  income.getDateAdded(),
                  SessionAccount.getInstance().getAccount().getSelectedBudget().endDate);
        } else {
          totalIncome = income.getAmount();
        }
      }
      default -> throw new IllegalArgumentException("No recurring type found.");
    }
    return totalIncome;
  }

  /**
   * Returns the total income of the budget by calculating the sum of all incomes.
   *
   * @return the total income of the budget
   */
  public int getTotalIncome() {
    totalIncome = 0;
    for (Income income : this.getIncomeList()) {
      totalIncome += calculateTotalIncome(income);
    }
    return totalIncome;
  }

  /**
   * Returns the total expense of the budget by calculating the sum of all expenses.
   *
   * @return the total expense of the budget
   */
  public int getTotalExpense() {
    totalExpense = 0;
    for (Expense expense : this.expenseList) {
      totalExpense += calculateTotalExpense(expense);
    }
    return totalExpense;
  }

  /**
   * Returns the net balance of the budget by subtracting the total expenses from the total income.
   *
   * @return the net balance of the budget
   */
  public int getNetBalance() {
    return getTotalIncome() - getTotalExpense();
  }

  /** Updates the expense category list by all the expense currently held in the budget. */
  private void updateExpenseCategoryList() {
    expenseCategoryList.clear();
    for (Expense expense : expenseList) {
      if (!expenseCategoryList.contains(expense.getExpenseCategory())) {
        expenseCategoryList.add(expense.getExpenseCategory());
      }
    }
  }

  /**
   * Adds an expense to the budget and updates the category list.
   *
   * @param expense the expense to add
   */
  public void addBudgetExpenses(Expense expense) {
    expenseList.add(expense);
    updateExpenseCategoryList();
  }

  /**
   * Adds an income to the budget and updates the category list.
   *
   * @param income the income to add
   */
  public void addBudgetIncome(Income income) {
    incomeList.add(income);
  }

  /**
   * Removes an expense from the budget and updates the category list.
   *
   * @param expense the expense to remove
   * @throws IndexOutOfBoundsException if the expense is not in the budget
   */
  public void removeBudgetExpenses(Expense expense) throws IndexOutOfBoundsException {
    if (expenseList.contains(expense)) {
      expenseList.remove(expense);
      updateExpenseCategoryList();
    } else {
      throw new IndexOutOfBoundsException("There is no such expense in the budget.");
    }
  }

  /**
   * Removes an income from the budget and updates the category list.
   *
   * @param income the income to remove
   * @throws IndexOutOfBoundsException if the income is not in the budget
   */
  public void removeBudgetIncome(Income income) throws IndexOutOfBoundsException {
    if (incomeList.contains(income)) {
      incomeList.remove(income);
    } else {
      throw new IndexOutOfBoundsException("There is no such income in the budget");
    }
  }

  /**
   * Returns the list of incomes associated with the budget.
   *
   * @return the list of incomes
   */
  public List<Income> getIncomeList() {
    return incomeList;
  }

  /**
   * Returns the list of expenses associated with the budget.
   *
   * @return the list of expenses
   */
  public List<Expense> getExpenseList() {
    return expenseList;
  }

  /**
   * Returns the list of expense categories associated with the budget.
   *
   * @return the list of expense categories
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
    Map<String, Integer> categories = new HashMap<>();
    for (Expense expense : this.getExpenseList()) {
      String category = expense.getExpenseCategory().getExpenseCategoryString();
      int amount = calculateTotalExpense(expense);
      if (categories.containsKey(category)) {
        categories.put(category, categories.get(category) + amount);
      } else {
        categories.put(category, amount);
      }
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : categories.entrySet()) {
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
    Map<String, Integer> categories = new HashMap<>();
    for (Income income : this.getIncomeList()) {
      String category = income.getIncomeCategory().getIncomeCategoryLabel();
      int amount = calculateTotalIncome(income);
      if (categories.containsKey(category)) {
        categories.put(category, categories.get(category) + amount);
      } else {
        categories.put(category, amount);
      }
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : categories.entrySet()) {
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
      incomeOrExpense.put(
          incomeString, incomeOrExpense.getOrDefault(incomeString, 0) + getTotalIncome());
    }
    for (Expense expense : this.getExpenseList()) {
      String expenseString = "Expense";
      incomeOrExpense.put(
          expenseString, incomeOrExpense.getOrDefault(expenseString, 0) + getTotalExpense());
    }
    List<PieChart.Data> data = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : incomeOrExpense.entrySet()) {
      data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }
    return data;
  }

  /**
   * Gets budget as string.
   *
   * @return the budget ass string
   */
  public StringBuilder getBudgetAssString() {
    StringBuilder sb = new StringBuilder();
    sb.append(budgetName).append("\n");
    sb.append(getStartDate()).append("\n");
    sb.append(getEndDate());
    return sb;
  }
}
