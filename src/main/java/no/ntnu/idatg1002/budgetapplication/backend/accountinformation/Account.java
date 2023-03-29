package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;

/**
 * Represents an account. Each account holds some information about that account.
 *
 * @author Simon Husås Houmb, Erik Bjørnsen
 * @version 1.0 (2023-03-15)
 */
public class Account {
  private String name;
  private String email;
  private String pinCode;
  private SecurityQuestion securityQuestion;
  private String securityAnswer;
  private final String accountNumber;
  private ArrayList<SavingsPlan> savingsPlans;
  private SavingsPlan selectedSavingsPlan;
  private ArrayList<Budget> budgets;
  private Budget selectedBudget;

  Random rand = new Random();

  /**
   * Creates a new account with a name, email, 4 digit pinCode, chosen securityQuestion and
   * securityAnswer. Each account also gets a randomly generated ID number, a collection of
   * savingsPlans, and a budget.
   *
   * @param name name of account owner.
   * @param email email of account owner.
   * @param pinCode 4-digit code chosen by account owner.
   * @param securityQuestion securityQuestion chosen by account owner.
   * @param securityAnswer answer to the securityQuestion.
   */
  public Account(
      String name,
      String email,
      String pinCode,
      SecurityQuestion securityQuestion,
      String securityAnswer) {
    this.name = name;
    setEmail(email);
    setPinCode(pinCode);
    this.securityQuestion = securityQuestion;
    setSecurityAnswer(securityAnswer);
    this.accountNumber = generateAccountNumber();
    this.savingsPlans = new ArrayList<>();
    this.budgets = new ArrayList<>();
  }

  /**
   * Returns the name of the account owner.
   *
   * @return the name of the account owner as a String.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the account owner to the provided name.
   *
   * @param name the name to be set.
   * @throws IllegalArgumentException "Account name must not be empty or blank" if name is blank or
   *     empty
   */
  public void setName(String name) throws IllegalArgumentException {
    if (name.isBlank() || name.isEmpty()) {
      throw new IllegalArgumentException("Account name must not be empty or blank.");
    } else {
      this.name = name;
    }
  }

  /**
   * Returns the email of the account owner.
   *
   * @return the email of the account owner as a String.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the account owner to the provided email.
   *
   * @param email the email to be set.
   * @throws IllegalArgumentException "Email must not be empty or blank." if email is empty or
   *     blank. "Email does not contain '@'." if email does not contain '@'. "Email already in use."
   *     if email is already in use.
   */
  public void setEmail(String email) throws IllegalArgumentException {
    if (email.isBlank() || email.isEmpty()) {
      throw new IllegalArgumentException("Email must not be empty or blank.");
    } else if (!email.contains("@")) {
      throw new IllegalArgumentException("Email does not contain '@'.");
    } else if (Database.getEmails().contains(email)) {
      throw new IllegalArgumentException("Email already in use.");
    } else {
      this.email = email;
    }
  }

  /**
   * Returns the pin code chosen by the account owner.
   *
   * @return the 4-digit pin code chosen by the account owner as a String.
   */
  public String getPinCode() {
    return pinCode;
  }

  /**
   * Sets the pinCode of the account to the provided PinCode as long as it is 4 digits. If the pin
   * code is not 4 digits, it returns false.
   *
   * @param pinCode the pinCode to be set.
   * @throws IllegalArgumentException "Pin code must only consist of numbers." if pin code has
   *     characters that are not numbers. "Pin code must consist of 4 digits." if pin code's length
   *     is not 4 digits.
   */
  public void setPinCode(String pinCode) throws IllegalArgumentException {
    if (!pinCode.matches("\\d+")) {
      throw new IllegalArgumentException("Pin code must only consist of numbers.");
    } else if (pinCode.length() != 4) {
      throw new IllegalArgumentException("Pin code must consist of 4 digits.");
    } else {
      this.pinCode = pinCode;
    }
  }

  /**
   * Returns the security question chosen by the account owner.
   *
   * @return the security question chosen by the account owner.
   */
  public SecurityQuestion getSecurityQuestion() {
    return securityQuestion;
  }

  /**
   * Sets the securityQuestion of the account to the provided security question.
   *
   * @param securityQuestion the securityQuestion to be set.
   */
  public void setSecurityQuestion(SecurityQuestion securityQuestion) {
    this.securityQuestion = securityQuestion;
  }

  /**
   * Returns the security answer of the security question.
   *
   * @return the security answer of the security question as a String.
   */
  public String getSecurityAnswer() {
    return securityAnswer;
  }

  /**
   * Sets the securityAnswer to the provided security answer.
   *
   * @param securityAnswer the securityAnswer to be set.
   * @throws IllegalArgumentException "Security answer must not be empty or blank." if security
   *     answer is empty or blank.
   */
  public void setSecurityAnswer(String securityAnswer) throws IllegalArgumentException {
    if (securityAnswer.isBlank() || securityAnswer.isEmpty()) {
      throw new IllegalArgumentException("Security answer must not be empty or blank.");
    } else {
      this.securityAnswer = securityAnswer;
    }
  }

  /**
   * Returns the account number.
   *
   * @return the accountNumber as a String.
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Returns a collection of the account's savings plans.
   *
   * @return the savingsPlans collection as a Map.
   */
  public ArrayList<SavingsPlan> getSavingsPlans() {
    return savingsPlans;
  }

  /**
   * Adds a savings plan to the account's savingsPlans collection as long as the savings plan does
   * not already exist or the savings plan name is not taken.
   *
   * @param savingsPlan the savingsPlan to be added.
   * @throws IllegalArgumentException "An instance of the savings plan already exists." if an
   *     instance of the savings plan already exists. "Savings plan goal name is taken." if name of
   *     savings plan is already taken.
   */
  public void addSavingsPlan(SavingsPlan savingsPlan) throws IllegalArgumentException {
    if (savingsPlans.contains(savingsPlan)) {
      throw new IllegalArgumentException("An instance of the savings plan already exists.");
    } else if (checkIfSavingsPlanNameIsTaken(savingsPlan)) {
      throw new IllegalArgumentException("Savings plan goal name is taken.");
    } else {
      this.savingsPlans.add(savingsPlan);
      initializeSelectedSavingsPlan(savingsPlan);
    }
  }

  private boolean checkIfSavingsPlanNameIsTaken(SavingsPlan savingsPlan) {
    boolean nameTaken = false;
    for (SavingsPlan savingsPlanForLoop : savingsPlans) {
      if (savingsPlanForLoop.getGoalName().equals(savingsPlan.getGoalName())) {
        // name already exists, do something
        // for example, return or throw an exception
        nameTaken = true;
      }
    }
    return nameTaken;
  }

  /**
   * Removes a savings plan to the account's savingsPlans collection.
   *
   * @param savingsPlan the savingsPlan to be added.
   */
  public void removeSavingsPlan(SavingsPlan savingsPlan) {
    this.savingsPlans.remove(savingsPlan);
  }

  /**
   * Returns the account's Budget.
   *
   * @return the account's Budget.
   */
  public ArrayList<Budget> getBudgets() {
    return budgets;
  }

  /**
   * Adds a budget to the account's budget collection as long as the budget does not already exist
   * or the budget name is not taken.
   *
   * @param budget the budget to be added.
   * @throws IllegalArgumentException "An instance of the budget already exists." if an instance of
   *     the budget already exists. "Budget name is taken." if the name of the budget is already
   *     taken.
   */
  public void addBudget(Budget budget) throws IllegalArgumentException {
    if (budgets.contains(budget)) {
      throw new IllegalArgumentException("An instance of the budget already exists.");
    } else if (checkIfBudgetNameIsTaken(budget)) {
      throw new IllegalArgumentException("Budget name is taken.");
    } else {
      this.budgets.add(budget);
      initializeSelectedBudget(budget);
    }
  }

  private boolean checkIfBudgetNameIsTaken(Budget budget) {
    boolean nameTaken = false;
    for (Budget budgetForLoop : budgets) {
      if (budgetForLoop.getBudgetName().equals(budget.getBudgetName())) {
        // name already exists, do something
        // for example, return or throw an exception
        nameTaken = true;
      }
    }
    return nameTaken;
  }

  /**
   * Removes a budget to the account's budget collection.
   *
   * @param budget the budget to be added.
   */
  public void removeBudget(Budget budget) {
    this.budgets.remove(budget);
  }

  public void initializeSelectedBudget(Budget budget) {
    if (budgets.size() == 1) { // means that the budget just entered is the first one
      selectedBudget = budget;
    }
  }

  public Budget getSelectedBudget() {
    return selectedBudget;
  }

  public void selectNextBudget() throws IndexOutOfBoundsException {
    if (budgets.size() > budgets.indexOf(selectedBudget)) {
      selectedBudget = budgets.get(budgets.indexOf(selectedBudget) + 1);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  public void selectPreviousBudget() throws IndexOutOfBoundsException {
    if (budgets.indexOf(selectedBudget) > 0) {
      selectedBudget = budgets.get(budgets.indexOf(selectedBudget) - 1);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  public void initializeSelectedSavingsPlan(SavingsPlan savingsPlan) {
    if (savingsPlans.size() == 1) { // means that the savingsplan just entered is the first one
      selectedSavingsPlan = savingsPlan;
    }
  }

  public SavingsPlan getSelectedSavingsPlan() {
    return selectedSavingsPlan;
  }

  public void selectNextSavingsPlan() throws IndexOutOfBoundsException {
    if (savingsPlans.size() > savingsPlans.indexOf(selectedSavingsPlan)) {
      selectedSavingsPlan = savingsPlans.get(savingsPlans.indexOf(selectedSavingsPlan) + 1);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  public void selectPreviousSavingsPlan() throws IndexOutOfBoundsException {
    if (savingsPlans.indexOf(selectedSavingsPlan) > 0) {
      selectedSavingsPlan = savingsPlans.get(savingsPlans.indexOf(selectedSavingsPlan) - 1);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Generates a random 14-digit AccountNumber as a String. The AccountNumber will be formatted in
   * this way: ID-**************
   *
   * @return the random AccountNumber as a String
   */
  private String generateAccountNumber() {
    boolean idTaken = true;
    StringBuilder id;
    do {
      id = new StringBuilder("ID-");

      for (int i = 0; i < 14; i++) {
        int n = rand.nextInt(10);
        id.append(n);
      }
      if (!Database.getAccounts().containsKey(id.toString())) {
        idTaken = false;
      }
    } while (idTaken);
    return id.toString();
  }

  /*
  // Currently not use
  private boolean saveAccountNumberToFile(String accountNumber) {
    String filename = "TakenAccountNumbers.txt";
    try {
      FileWriter writer = new FileWriter(filename, true);
      writer.write(accountNumber + "\n");
      writer.close();
      System.out.println("Successfully wrote to file " + filename);
      return true;
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      return false;
    }
  }
  */

  @Override
  public String toString() {
    return "Account{"
        + "name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", pinCode='"
        + pinCode
        + '\''
        + ", securityQuestion="
        + securityQuestion
        + ", securityAnswer='"
        + securityAnswer
        + '\''
        + ", accountNumber='"
        + accountNumber
        + '\''
        + ", savingsPlans="
        + savingsPlans
        + ", budgets="
        + budgets
        + '}';
  }
}
