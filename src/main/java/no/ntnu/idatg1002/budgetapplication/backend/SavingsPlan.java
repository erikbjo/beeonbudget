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
   * The constructor creates a new instance of SavingsPlan and sets a new goal,
   * with total amount saved.
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

  /**
   * Getter for goalName.
   *
   * @return Name of goal as a String.
   */
  public String getGoalName() {
    return goalName;
  }

  /**
   * Setter for goalName.
   *
   * @param goalName String that describes the goal
   * @return Returns a boolean that says if the input is wrong
   */
  public boolean setGoalName(String goalName) {
    boolean goalNameCheck = true;
    if (goalName == null) {
      goalNameCheck = false;
    } else {
      this.goalName = goalName;
    }
    return goalNameCheck;
  }

  /**
   * Getter for totalGoalAmount.
   *
   * @return totalGoalAmount as int.
   */
  public int getTotalGoalAmount() {
    return totalGoalAmount;
  }

  /**
   * Setter for totalGoalAmount.
   *
   * @param totalGoalAmount New totalGoalAmount.
   * @return Returns a boolean that says if the input is wrong.
   */
  public boolean setTotalGoalAmount(int totalGoalAmount) {
    boolean totalGoalAmountCheck;
    totalGoalAmountCheck = true;
    if (totalGoalAmount <= 0) {
      totalGoalAmountCheck = false;
    } else {
      this.totalGoalAmount = totalGoalAmount;
    }
    return totalGoalAmountCheck;
  }

  /**
   * Getter for totalSaved.

   * @return Returns totalSaved as int.
   */
  public int getTotalSaved() {
    return totalSaved;
  }

  /**
   * Setter for totalSaved.

   * @param totalSaved New totalSaved.
   */
  public void setTotalSaved(int totalSaved) {
    this.totalSaved = totalSaved;
  }

  /**
   * Getter for wantedSavingTime.

   * @return Returns wantedSavingTime as int.
   */
  public int getWantedSavingTime() {
    return wantedSavingTime;
  }

  /**
   * Setter for wantedSavingTime.

   * @param wantedSavingTime New wantedSavingTime.
   * @return Returns a boolean that says if the input is wrong.
   */
  public boolean setWantedSavingTime(int wantedSavingTime) {
    boolean wantedTimeCheck;
    wantedTimeCheck = true;
    if (wantedSavingTime <= 1) {
      wantedTimeCheck = false;

    } else {
      this.wantedSavingTime = wantedSavingTime;
    }
    return wantedTimeCheck;
  }

  /**
   * Getter for wantedMonthlySavingAmount.

   * @return Returns wantedMonthlySavingAmount as int.
   */
  public int getWantedMonthlySavingAmount() {
    return wantedMonthlySavingAmount;
  }

  /**
   * Setter for wantedMonthlySavingAmount.

   * @param wantedMonthlySavingAmount New wantedMonthlySavingAmount.
   * @return Returns a boolean that says if the input is wrong.
   */
  public boolean setWantedMonthlySavingAmount(int wantedMonthlySavingAmount) {

    boolean wantedMonthlySavAmCheck;
    wantedMonthlySavAmCheck = true;
    if (wantedMonthlySavingAmount <= 1) {
      wantedMonthlySavAmCheck = false;

    } else {
      this.wantedMonthlySavingAmount = wantedMonthlySavingAmount;
    }
    return wantedMonthlySavAmCheck;
  }

  /**
   * Estimates amount of months needed to reach the goal, given a user-specified
   * amount they want to save per month.
   *
   * @return Estimated saving time in months.
   */
  public int estimateSavingTime() {
    int estimatedSavTime = totalGoalAmount / wantedMonthlySavingAmount;
    return estimatedSavTime;
  }

  /**
   * Estimates how much money per month is needed
   * to reach the goal in a given amount of time.
   *
   * @return Amount of money needed is savings per month.
   */
  public int estimateMonthlySavingAmount() {
    int estimatedMonthlySavA = totalGoalAmount / wantedSavingTime;
    return estimatedMonthlySavA;
  }
}

