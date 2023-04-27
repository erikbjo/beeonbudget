package no.ntnu.idatg1002.budgetapplication.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents a savings plan. Each savings plan holds some information about that plan.
 *
 * @author Igor Dzugaj, Erik Bjørnsen, Simon Husås Houmb
 * @version 2.0
 */
@Entity
public class SavingsPlan {
  @Id
  @GeneratedValue
  private Long id;
  private String goalName;
  private int totalGoalAmount;
  private int totalSaved;
  private LocalDate startDate;
  private LocalDate endDate;
  private int wantedSavingTime;
  private int wantedMonthlySavingAmount;

  /**
   * Instantiates a new Savings plan.
   *
   * @param goalName the goal name.
   * @param totalGoalAmount the total goal amount.
   * @param startDate the start date of the savings plan.
   * @param endDate the end date of the savings plan.
   * @throws IllegalArgumentException if goalName is null or empty or if startDate is after endDate
   */
  public SavingsPlan(String goalName, int totalGoalAmount,
      LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
    if (goalName == null || goalName.trim().isEmpty()) {
      throw new IllegalArgumentException("Goal name must not be empty or blank.");
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date must be before end date.");
    }
    this.goalName = goalName;
    this.totalGoalAmount = totalGoalAmount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalSaved = 0;
  }

  public SavingsPlan() {

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
   * @throws IllegalArgumentException if goalName is null or empty
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
   * @throws IllegalArgumentException if totalGoalAmount is less than or equal to zero
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
   * Returns the percentage of the amount saved compared to the goal.
   * The value is between 1 and 0, where e.g. 45% would be 0.45.
   *
   * @return the percentage saved as an int.
   */
  public double getTotalSavedPercentage() {
    return (double) getTotalSaved() / getTotalGoalAmount();
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
   * Returns the start date of the savings plan.
   *
   * @return the start date of the savings plan as LocalDate.
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Deposits an amount in the savings plan.
   *
   * @param amount the amount to deposit as an int.
   */
  public void deposit(int amount) {
    setTotalSaved(getTotalSaved() + amount);
  }

  /**
   * Sets the start date of the savings plan.
   *
   * @param startDate the start date to set.
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Returns the end date of the savings plan.
   *
   * @return the end date of the savings plan as LocalDate.
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date of the savings plan.
   *
   * @param endDate the end date to set.
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Sets wanted saving time.
   *
   * @param wantedSavingTime the wanted saving time
   * @throws IllegalArgumentException if wantedSavingTime is less than or equal to zero
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
   * @throws IllegalArgumentException if wantedMonthlySavingAmount is less than or equal to zero
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
   * Returns a string with both StartTime and EndTime.
   *
   * @return A text with both start date and end date as a String.
   */
  public String getStartToEndString() {
    return String.format(
        "%s - %s",
        getStartDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
        getEndDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
  }
}
