package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * This class represents the saving goal a user can make. It holds the required information
 * and has methods that can estimate the time and/or money per month required to reach the goal
 *
 * @author Igor Mikolaj Dzugaj
 */

public class SavingsPlan {
  private String goalName;
  private int totalGoalAmount;
  private int totalSaved;
  private int wantedSavingTime;
  private int wantedMonthlySavingAmount;


  /**
   * The constructor creates a new instance of SavingsPlan and sets a new goal, with total amount saved
   *
   * @param goalName Name of the thing user wants to save money for
   * @param totalGoalAmount Total money needed to reach the goal
   * @param totalSaved Total money saved towards the goal
   */
  public SavingsPlan(String goalName, int totalGoalAmount, int totalSaved) {
    setGoalName(goalName);
    setTotalGoalAmount(totalGoalAmount);
    this.totalSaved = totalSaved;
  }

  public String getGoalName() {
    return goalName;
  }

  public void setGoalName(String goalName) {
    boolean goalNameCheck = true;
    if (goalName == null) {
      goalNameCheck = false;
      return;
    } else {
      this.goalName = goalName;
    }
  }

  public int getTotalGoalAmount() {
    return totalGoalAmount;
  }

  public void setTotalGoalAmount(int totalGoalAmount) {
    boolean totalGoalAmountCheck;
    totalGoalAmountCheck = true;
    if (totalGoalAmount <= 0) {
      totalGoalAmountCheck = false;
      return;
    } else {
      this.totalGoalAmount = totalGoalAmount;
    }
  }

  public int getTotalSaved() {
    return totalSaved;
  }

  public void setTotalSaved(int totalSaved) {
    this.totalSaved = totalSaved;
  }

  public int getWantedSavingTime() {
    return wantedSavingTime;
  }

  public void setWantedSavingTime(int wantedSavingTime) {
    boolean wantedTimeCheck;
    wantedTimeCheck = true;
    if (wantedSavingTime <= 1) {
      wantedTimeCheck = false;
      return;
    } else {
      this.wantedSavingTime = wantedSavingTime;
    }
  }

  public int getWantedMonthlySavingAmount() {
    return wantedMonthlySavingAmount;
  }

  public void setWantedMonthlySavingAmount(int wantedMonthlySavingAmount) {

    boolean wantedMonthlySavAmCheck;
    wantedMonthlySavAmCheck = true;
    if (wantedMonthlySavingAmount <= 1) {
      wantedMonthlySavAmCheck = false;
      return;
    } else {
      this.wantedMonthlySavingAmount = wantedMonthlySavingAmount;
    }
  }

  /**
   * Estimates amount of months needed to reach the goal, given a user-specified
   * amount they want to save per month
   *
   * @return Estimated saving time in months
   */
  public int estimateSavingTime() {
    int estimatedSavTime = totalGoalAmount / wantedMonthlySavingAmount;
    return estimatedSavTime;
  }

  /**
   * Estimates how much money per month is needed to reach the goal in a
   * given amount of time
   *
   * @return Amount of money needed is savings per month
   */
  public int estimateMonthlySavingAmount() {
    int estimatedMonthlySavA = totalGoalAmount / wantedSavingTime;
    return estimatedMonthlySavA;
  }
}

