package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * Represents a savings plan. Each savings plan holds some information about that plan.
 *
 * @author Igor Dzugaj, Erik Bjørnsen
 * @version 2.0
 */
public class SavingsPlan {
  private String goalName;
  private int totalGoalAmount;
  private int totalSaved;
  private int wantedSavingTime;
  private int wantedMonthlySavingAmount;
  private int deposit;

  /**
   * Instantiates a new Savings plan.
   *
   * @param goalName the goal name
   * @throws IllegalArgumentException "Goal name must not be empty or blank." and/or ""Total goal
   *     amount must be above zero." for totalGoalAmount < 0
   */
  public SavingsPlan(String goalName) throws IllegalArgumentException {

    if (goalName == null || goalName.trim().isEmpty()) {
      throw new IllegalArgumentException("Goal name must not be empty or blank.");
    }

    this.goalName = goalName;
  }

  /**
   * Gets goal name.
   *
   * @return the goal name
   */
  public String getGoalName() {
    return goalName;
  }

  /**
   * Sets goal name.
   *
   * @param goalName the goal name
   * @throws IllegalArgumentException "Goal name must not be empty or blank."
   */
  public void setGoalName(String goalName) throws IllegalArgumentException {
    if (goalName == null || goalName.trim().isEmpty()) {
      throw new IllegalArgumentException("Goal name must not be empty or blank.");
    }

    this.goalName = goalName;
  }

  /**
   * Gets total goal amount.
   *
   * @return the total goal amount
   */
  public int getTotalGoalAmount() {
    return totalGoalAmount;
  }

  /**
   * Sets total goal amount.
   *
   * @param totalGoalAmount the total goal amount
   * @throws IllegalArgumentException "Total goal amount must be above zero." for totalGoalAmount <=
   *     0
   */
  public void setTotalGoalAmount(int totalGoalAmount) throws IllegalArgumentException {
    if (totalGoalAmount <= 0) {
      throw new IllegalArgumentException("Total goal amount must be above zero.");
    }

    this.totalGoalAmount = totalGoalAmount;
  }

  /**
   * Gets total saved.
   *
   * @return the total saved
   */
  public int getTotalSaved() {
    return totalSaved;
  }

  /**
   * Sets total saved.
   *
   * @param totalSaved the total saved
   */
  public void setTotalSaved(int totalSaved) {

    this.totalSaved = totalSaved;
  }

  /**
   * Gets wanted saving time.
   *
   * @return the wanted saving time
   */
  public int getWantedSavingTime() {
    return wantedSavingTime;
  }

  /**
   * Sets wanted saving time.
   *
   * @param wantedSavingTime the wanted saving time
   * @throws IllegalArgumentException "Wanted saving time amount must be above zero." for
   *     wantedSavingTime <= 0
   */
  public void setWantedSavingTime(int wantedSavingTime) throws IllegalArgumentException {
    if (wantedSavingTime <= 0) {
      throw new IllegalArgumentException("Wanted saving time amount must be above zero.");
    }
    this.wantedSavingTime = wantedSavingTime;
  }

  /**
   * Gets wanted monthly saving amount.
   *
   * @return the wanted monthly saving amount
   */
  public int getWantedMonthlySavingAmount() {
    return wantedMonthlySavingAmount;
  }

  /**
   * Sets wanted monthly saving amount.
   *
   * @param wantedMonthlySavingAmount the wanted monthly saving amount
   * @throws IllegalArgumentException "Wanted monthly saving amount must be above zero." for
   *     wantedMonthlySavingAmount <= 0
   */
  public void setWantedMonthlySavingAmount(int wantedMonthlySavingAmount)
      throws IllegalArgumentException {
    if (wantedMonthlySavingAmount <= 0) {
      throw new IllegalArgumentException("Wanted monthly saving amount must be above zero.");
    }
    this.wantedMonthlySavingAmount = wantedMonthlySavingAmount;
  }

  /**
   * Estimate saving time int.
   *
   * @return the int
   */
  public int getEstimatedSavingTime() {
    return totalGoalAmount / wantedMonthlySavingAmount;
  }

  /**
   * Estimate monthly saving amount int.
   *
   * @return the int
   */
  public int getEstimatedMonthlySavingAmount() {
    return totalGoalAmount / wantedSavingTime;
  }

  /**
   * Sets the deposit.
   *
   * @param deposit money deposited into savings.
   */
  public void setDeposit(int deposit) {
    this.deposit = deposit;
  }

  /** Adds the previous savings and the deposit. Resets deposit to 0. */
  public void addSavings() {
    int newSavings = getTotalSaved() + deposit;
    setTotalSaved(newSavings);
    setDeposit(0);
  }
}
