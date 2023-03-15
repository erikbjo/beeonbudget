package no.ntnu.idatg1002.budgetapplication.backend.savings;

public class SavingsPlan {
  private String goalName;
  private int totalGoalAmount;
  private int totalSaved;

  public SavingsPlan(String goalName, int totalGoalAmount, int totalSaved) {
    this.goalName = goalName;
    this.totalGoalAmount = totalGoalAmount;
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

}

