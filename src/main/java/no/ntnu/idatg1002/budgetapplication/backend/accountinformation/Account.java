package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.savings.SavingsPlan;

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
  private String accountNumber;
  private Map<String, SavingsPlan> savingsPlans;
  private Budget budget;

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
    this.budget = new Budget("testBudget");
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
  public void setName(String name) {
    this.name = name;
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
  public boolean setEmail(String email) {
    if (!email.contains("@") && !Database.getEmails().contains(email)) {
      return false;
    } else {
      this.email = email;
      return true;
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
   * @return true if the provided pinCode is 4 digits, if not it returns false.
   */
  public boolean setPinCode(String pinCode) {
    if (pinCode.length() != 4 || !pinCode.matches("\\d+")) {
      return false;
    } else {
      this.pinCode = pinCode;
      return true;
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
  public void setSecurityAnswer(String securityAnswer) {
    this.securityAnswer = securityAnswer;
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
   * Sets the accountNumber of the account to the provided name.
   *
   * @param accountNumber the accountNumber to be set.
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
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
   * Adds a savings plan to the account's savingsPlans collection.
   *
   * @param savingsPlan the savingsPlan to be added.
   */
  public void addSavingsPlan(SavingsPlan savingsPlan) {
    //this.savingsPlans.put(savingsPlan.getGoalName(), savingsPlan);
  }

  /**
   * Returns the account's Budget.
   *
   * @return the account's Budget.
   */
  public Budget getBudget() {
    return budget;
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