package no.ntnu.idatg1002.budgetapplication.backend.savings;

public class SavingsPlan {
  private String goalName;
  private int totalGoalAmount;
  private int totalSaved;
  private int wantedSavingTime;
  private int wantedMonthlySavingAmount;

  public SavingsPlan(String goalName, int totalGoalAmount, int totalSaved) {
    setGoalName(goalName);
    setTotalGoalAmount(totalGoalAmount);
    this.totalSaved = totalSaved;
  }

  public String getGoalName() {
    return goalName;
  }

  public void setGoalName(String goalName) {
    boolean goalNameCheck;
    goalNameCheck = true;
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
    if (wantedMonthlySavingAmount <= 0) {
      wantedMonthlySavAmCheck = false;
      return;
    } else {
      this.wantedMonthlySavingAmount = wantedMonthlySavingAmount;
    }
  }

  public int estimateSavingTime() {
    int estimatedSavTime = totalGoalAmount / wantedMonthlySavingAmount;
    return estimatedSavTime;
  }

  public int estimateMonthlySavingAmount() {
    int estimatedMonthlySavA = totalGoalAmount / wantedSavingTime;
    return estimatedMonthlySavA;
  }
}

