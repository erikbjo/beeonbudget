package no.ntnu.idatg1002.budgetapplication.backend.savings;

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

  public SavingsPlan(String goalName, int totalGoalAmount, int totalSaved)
      throws IllegalArgumentException {

    if (goalName == null || goalName.trim().isEmpty()) {
      throw new IllegalArgumentException("Goal name must not be empty or blank");
    }
    if (totalGoalAmount < 0) {
      throw new IllegalArgumentException("Total goal amount must be non-negative");
    }

    this.goalName = goalName;
    this.totalGoalAmount = totalGoalAmount;
    this.totalSaved = totalSaved;
  }

  public String getGoalName() {
    return goalName;
  }

  public void setGoalName(String goalName) throws IllegalArgumentException {
    if (goalName == null || goalName.trim().isEmpty()) {
      throw new IllegalArgumentException("Goal name must not be empty or blank");
    }

    this.goalName = goalName;
  }

  public int getTotalGoalAmount() {
    return totalGoalAmount;
  }

  public void setTotalGoalAmount(int totalGoalAmount) throws IllegalArgumentException {
    if (totalGoalAmount < 0) {
      throw new IllegalArgumentException("Total goal amount must be non-negative");
    }

    this.totalGoalAmount = totalGoalAmount;
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

  public void setWantedSavingTime(int wantedSavingTime) throws IllegalArgumentException {
    if (wantedSavingTime < 0) {
      throw new IllegalArgumentException("Wanted saving time amount must be non-negative");
    }
    this.wantedSavingTime = wantedSavingTime;
  }

  public int getWantedMonthlySavingAmount() {
    return wantedMonthlySavingAmount;
  }

  public void setWantedMonthlySavingAmount(int wantedMonthlySavingAmount)
      throws IllegalArgumentException {
    if (wantedMonthlySavingAmount < 0) {
      throw new IllegalArgumentException("Wanted monthly saving amount must be non-negative");
    }
    this.wantedMonthlySavingAmount = wantedMonthlySavingAmount;
  }

  public int estimateSavingTime() {
    int estimatedSavTime = totalGoalAmount / wantedMonthlySavingAmount;
    return estimatedSavTime;
  }

  public int estimateMonthlySavingAmount() {
    int estimatedMonthlySaveAmount = totalGoalAmount / wantedSavingTime;
    return estimatedMonthlySaveAmount;
  }
}
