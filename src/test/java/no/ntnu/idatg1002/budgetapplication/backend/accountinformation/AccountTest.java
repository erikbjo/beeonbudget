package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;import no.ntnu.idatg1002.budgetapplication.backend.Category;import no.ntnu.idatg1002.budgetapplication.backend.Expense;import no.ntnu.idatg1002.budgetapplication.backend.Income;import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.accountinformation.Account;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AccountTest {
  private Account account;
  private Budget budget;
  private Income income;
  private Expense expense;
  private SavingsPlan savingsPlan;

  @BeforeEach
  void setUp() {
    account = new Account("Test", "test@test.com", "1234",
        SecurityQuestion.CAR_BRAND, "BMW");

    budget = new Budget("Test budget");
    income = new Income(50, "Test income", Category.HOUSING, RecurringType.NONRECURRING);
    expense = new Expense(50, "Test expense", Category.HOUSING, RecurringType.NONRECURRING);
    budget.addBudgetIncome(income);
    budget.addBudgetExpenses(expense);

    savingsPlan = new SavingsPlan("Test goal", 100, 50);
  }

  @Nested
  class SetEmailTest {
    @Test
    void emailHasAtSign() {
      assertTrue(account.setEmail("simon@gmail.com"));
      assertEquals("simon@gmail.com", account.getEmail());
    }

    @Test
    void emailHasNoAtSign() {
      assertFalse(account.setEmail("simon.gmail.com"));
      assertEquals("test@test.com", account.getEmail());
    }
  }

  @Nested
  class securityQuestionTests {
    @Test
    void getAndSetSecurityQuestionPositive() {
      account.setSecurityQuestion(SecurityQuestion.FIRST_PET);
      assertEquals(SecurityQuestion.FIRST_PET, account.getSecurityQuestion());
    }
    @Test
    void getAndSetSecurityAnswerPositive() {
      account.setSecurityAnswer("Test answer");
      assertEquals("Test answer", account.getSecurityAnswer());
    }
  }
  @Nested
  class simpleGettersAndSettersTest {
    @Test
    void getNameReturnsCorrectName() {
      assertEquals("Test", account.getName());
    }
    @Test
    void getEmailReturnsCorrectEmail() {
      assertEquals("test@test.com", account.getEmail());
    }
    @Test
    void setNameNeedsToBeNotBlankAndNotNull() {
      account.setName("Control name");
      account.setName("");
      account.setName(" ");

      assertEquals("Control name", account.getName());
    }
  }

  @Nested
  class BudgetTest {
    @Test
    void addBudgetPositiveTest() {
      account.addBudget(budget);
      assertTrue(account.getBudgets().containsValue(budget));
    }
    @Test
    void removeBudgetPositiveTest() {
      account.addBudget(budget);
      account.removeBudget(budget);
      assertFalse(account.getBudgets().containsValue(budget));
    }
  }

  @Nested
  class SavingPlanTest {
    @Test
    void addSavingsPlanPositiveTest() {
      account.addSavingsPlan(savingsPlan);
      assertTrue(account.getSavingsPlans().containsValue(savingsPlan));
    }
    @Test
    void removeSavingsPlanPositiveTest() {
      account.addSavingsPlan(savingsPlan);
      account.removeSavingsPlan(savingsPlan);
      assertFalse(account.getSavingsPlans().containsValue(savingsPlan));
    }
  }
  @Nested
  class SetPinCodeTest {
    @Test
    void pinCodeHasFourDigits() {
      assertTrue(account.setPinCode("0001"));
      assertEquals("0001", account.getPinCode());
    }

    @Test
    void pinCodeHasLessThanFourDigits() {
      assertFalse(account.setPinCode("001"));
      assertEquals("1234", account.getPinCode());
    }

    @Test
    void pinCodeHasMoreThanFourDigits() {
      assertFalse(account.setPinCode("00001"));
      assertEquals("1234", account.getPinCode());
    }

    @Test
    void pinCodeHasOnlyLetters() {
      assertFalse(account.setPinCode("code"));
      assertEquals("1234", account.getPinCode());
    }

    @Test
    void pinCodeHasLettersAndDigits() {
      assertFalse(account.setPinCode("id09"));
      assertEquals("1234", account.getPinCode());
    }
  }

  @Nested
  class GenerateAccountNumberTest {
    @Test
    void accountNumberIsCorrectFormat() {
      assertTrue(account.getAccountNumber().startsWith("ID-"));
      assertEquals(17, account.getAccountNumber().length());
    }
  }
  @Test
  void toStringPositiveTest() {
    System.out.println(account.toString());
    String expected = "Account{name='" + account.getName()
        + "', email='" + account.getEmail()
        + "', pinCode='" + account.getPinCode()
        + "', securityQuestion=" + account.getSecurityQuestion()
        + ", securityAnswer='" + account.getSecurityAnswer()
        + "', accountNumber='" + account.getAccountNumber()
        + "', savingsPlans=" + account.getSavingsPlans()
        + ", budgets=" + account.getBudgets()
        + "}";
    assertEquals(expected, account.toString());
  }
}