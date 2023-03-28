package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;

/**
 * Represents an account. Each account holds some information about that account.
 *
 * @author Simon Husås Houmb
 * @version 1.0 (2023-03-15)
 */
public class Account {
  private String name;
  private String email;
  private String pinCode;
  private SecurityQuestion securityQuestion;
  private String securityAnswer;
  private final String accountNumber;
  private Map<String, SavingsPlan> savingsPlans;
  private Map<String, Budget> budgets;

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
  public Account(String name, String email, String pinCode, SecurityQuestion securityQuestion,
      String securityAnswer) {
    this.name = name;
    setEmail(email);
    setPinCode(pinCode);
    this.securityQuestion = securityQuestion;
    this.securityAnswer = securityAnswer;
    this.accountNumber = generateAccountNumber();
    this.savingsPlans = new HashMap<>();
    this.budgets = new HashMap<>();
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
   * Sets the pinCode of the account to the provided PinCode as long as it is 4 digits. If the
   * pin code is not 4 digits, it returns false.
   *
   * @param pinCode the pinCode to be set.
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
  public Map<String, SavingsPlan> getSavingsPlans() {
    return savingsPlans;
  }

  /**
   * Adds a savings plan to the account's savingsPlans collection as long as the savings plan
   * does not already exist or the savings plan name is not taken.
   *
   * @param savingsPlan the savingsPlan to be added.
   */
  public void addSavingsPlan(SavingsPlan savingsPlan) throws IllegalArgumentException {
    if (savingsPlans.containsValue(savingsPlan)) {
      throw new IllegalArgumentException("An instance of the savings plan already exists.");
    } else if (savingsPlans.containsKey(savingsPlan.getGoalName())) {
      throw new IllegalArgumentException("Savings plan goal name is taken.");
    } else {
      this.savingsPlans.put(savingsPlan.getGoalName(), savingsPlan);
    }
  }

  /**
   * Removes a savings plan to the account's savingsPlans collection.
   *
   * @param savingsPlan the savingsPlan to be added.
   */
  public void removeSavingsPlan(SavingsPlan savingsPlan) {
    this.savingsPlans.remove(savingsPlan.getGoalName(), savingsPlan);
  }


  /**
   * Returns the account's Budget.
   *
   * @return the account's Budget.
   */
  public Map<String, Budget> getBudgets() {
    return budgets;
  }

  /**
   * Adds a budget to the account's budget collection as long as the budget
   * does not already exist or the budget name is not taken.
   *
   * @param budget the budget to be added.
   */
  public void addBudget(Budget budget) throws IllegalArgumentException {
    if (budgets.containsValue(budget)) {
      throw new IllegalArgumentException("An instance of the budget already exists.");
    } else if (budgets.containsKey(budget.getBudgetName())) {
      throw new IllegalArgumentException("Budget name is taken.");
    } else {
      this.budgets.put(budget.getBudgetName(), budget);
    }
  }

  /**
   * Removes a budget to the account's budget collection.
   *
   * @param budget the budget to be added.
   */
  public void removeBudget(Budget budget) {
    this.budgets.remove(budget.getBudgetName(), budget);
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
        + "name='" + name + '\''
        + ", email='" + email + '\''
        + ", pinCode='" + pinCode + '\''
        + ", securityQuestion=" + securityQuestion
        + ", securityAnswer='" + securityAnswer + '\''
        + ", accountNumber='" + accountNumber + '\''
        + ", savingsPlans=" + savingsPlans
        + ", budgets=" + budgets
        + '}';
  }
}
