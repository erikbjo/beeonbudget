package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
@Entity(name = "Account")
@Table(name = "account")
public class Account {
  @Id @GeneratedValue private String id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private final List<SavingsPlan> savingsPlans = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private final List<Budget> budgets = new ArrayList<>();

  private String name;
  private String email;
  private String pinCode;
  private SecurityQuestion securityQuestion;
  private String securityAnswer;
  private Integer currentSavingsPlanIndex = null;
  private Integer currentBudgetIndex = null;
  @Transient private Random rand;

  public Account() {}

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
    } else if (AccountDAO.getInstance().getAllEmails().contains(email)) {
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
   * Returns the account id.
   *
   * @return the id as a String.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns a collection of the account's savings plans.
   *
   * @return the savingsPlans collection as a Map.
   */
  public List<SavingsPlan> getSavingsPlans() {
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
      currentSavingsPlanIndex = this.savingsPlans.indexOf(savingsPlan);
    }
  }

  private boolean checkIfSavingsPlanNameIsTaken(SavingsPlan savingsPlan) {
    boolean nameTaken = false;
    for (SavingsPlan savingsPlanForLoop : savingsPlans) {
      if (savingsPlanForLoop.getGoalName().equals(savingsPlan.getGoalName())) {
        // name already exists, do something
        // for example, return or throw an exception
        nameTaken = true;
        break;
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
    updateSelectedSavingsPlan();
  }

  /**
   * Returns the account's Budget.
   *
   * @return the account's Budget.
   */
  public List<Budget> getBudgets() {
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
    } else {
      this.budgets.add(budget);
      currentBudgetIndex = this.budgets.indexOf(budget);
    }
  }

  public Integer getCurrentBudgetIndex() {
    return currentBudgetIndex;
  }

  /** Initialize selected budget. */
  public void initializeSelectedBudget() {
    if (!budgets.isEmpty()) {
      currentBudgetIndex = 0;
    } else {
      currentBudgetIndex = null;
    }
  }

  /**
   * Removes a budget from the account's budget collection.
   *
   * @param budget the budget to be removed.
   */
  public void removeBudget(Budget budget) {
    this.budgets.remove(budget);
    updateSelectedBudget();
  }

  private void updateSelectedBudget() {
    if (budgets.isEmpty()) {
      currentBudgetIndex = null;
    } else if (currentBudgetIndex > budgets.size() - 1) {
      selectPreviousBudget();
    } else if (currentBudgetIndex < budgets.size() - 1) {
      selectNextBudget();
    } else {
      initializeSelectedBudget();
    }
  }

  private void updateSelectedSavingsPlan() {
    if (savingsPlans.isEmpty()) {
      currentSavingsPlanIndex = null;
    } else if (currentSavingsPlanIndex > savingsPlans.size() - 1) {
      selectPreviousSavingsPlan();
    } else if (currentSavingsPlanIndex < savingsPlans.size() - 1) {
      selectNextSavingsPlan();
    } else {
      initializeSelectedSavingsPlan();
    }
  }

  /**
   * Gets selected budget.
   *
   * @return the selected budget
   */
  public Budget getSelectedBudget() {
    if (currentBudgetIndex != null) {
      return budgets.get(currentBudgetIndex);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  /** Select next budget in budgets arraylist. */
  public void selectNextBudget() {
    if (currentBudgetIndex < budgets.size() - 1) {
      currentBudgetIndex += 1;
    } else {
      currentBudgetIndex = 0;
    }
  }

  /** Select previous budget in budgets arraylist. */
  public void selectPreviousBudget() {
    if (currentBudgetIndex > 0 && !budgets.isEmpty()) {
      currentBudgetIndex -= 1;
    } else {
      currentBudgetIndex = budgets.size() - 1;
    }
  }

  /** Initialize selected savings plan. */
  public void initializeSelectedSavingsPlan() {
    if (savingsPlans.size() == 1) { // means that the budget just entered is the first one
      currentSavingsPlanIndex = 0;
    }
  }

  /**
   * Gets selected savings plan.
   *
   * @return the selected savings plan
   */
  public SavingsPlan getSelectedSavingsPlan() {
    if (currentSavingsPlanIndex != null) {
      return savingsPlans.get(currentSavingsPlanIndex);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  /** Select next savings plan in savings plan arraylist. */
  public void selectNextSavingsPlan() {
    if (currentSavingsPlanIndex < savingsPlans.size() - 1) {
      currentSavingsPlanIndex += 1;
    } else {
      currentSavingsPlanIndex = 0;
    }
  }

  /** Select previous savings plan in savings plan arraylist. */
  public void selectPreviousSavingsPlan() {
    if (currentSavingsPlanIndex > 0 && !savingsPlans.isEmpty()) {
      currentSavingsPlanIndex -= 1;
    } else {
      currentSavingsPlanIndex = savingsPlans.size() - 1;
    }
  }

  /**
   * Generates a random 14-digit AccountNumber as a String. The AccountNumber will be formatted in
   * this way: ID-**************
   *
   * @return the random AccountNumber as a String
   */
  private String generateAccountNumber() {
    rand = new Random();
    boolean idTaken = true;
    StringBuilder stringBuilderId;
    do {
      stringBuilderId = new StringBuilder("ID-");

      for (int i = 0; i < 14; i++) {
        int n = this.rand.nextInt(10);
        stringBuilderId.append(n);
      }
      idTaken = false;
    } while (idTaken);
    return stringBuilderId.toString();
  }
}
