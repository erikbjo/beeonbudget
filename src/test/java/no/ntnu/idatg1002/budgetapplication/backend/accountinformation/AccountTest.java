package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Category;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AccountTest {
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

  @AfterEach
  void tearDown() {
    for (Account a : Database.getAccounts().values()) {
      a = null;
    }
    Database.getAccounts().clear();
    Database.getEmails().clear();
  }

  @Nested
  class SetEmailTest {
    @Test
    void emailHasAtSign() {
      assertDoesNotThrow(() -> account.setEmail("simon@gmail.com"));
      assertEquals("simon@gmail.com", account.getEmail());
    }

    @Test
    void emailHasNoAtSign() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> account.setEmail("simon.gmail.com"));
      assertEquals("Email does not contain '@'.", thrown.getMessage());
    }

    @Test
    void emailAlreadyInUse() {
      Account account2 = new Account("Erik", "simon@gmail.com", "4444",
          SecurityQuestion.FAVORITE_FOOD, "Pizza");
      Database.addAccount(account2);
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> account.setEmail("simon@gmail.com"));
      assertEquals("Email already in use.", thrown.getMessage());
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
  class addSavingsPlanTest {
    @Test
    void addNewSavingsPlanWithNotTakenName() {
      assertTrue(account.addSavingsPlan(
          new SavingsPlan("My goal", 100, 0)));
    }

    @Test
    void addNewSavingsPlanWithTakenName() {
      account.addSavingsPlan(new SavingsPlan("My goal", 100, 0));
      assertFalse(account.addSavingsPlan(
          new SavingsPlan("My goal", 100, 0)));
    }

    @Test
    void addExistingSavingsPlan() {
      SavingsPlan testSavingsPlan =
          new SavingsPlan("My goal", 100, 0);
      account.addSavingsPlan(testSavingsPlan);
      assertFalse(account.addSavingsPlan(testSavingsPlan));
    }
  }

  @Test
  void removeSavingsPlanTest() {
    account.addSavingsPlan(savingsPlan);
    account.removeSavingsPlan(savingsPlan);
    assertFalse(account.getSavingsPlans().containsValue(savingsPlan));
  }
    @Nested
    class addBudgetTest {

      @Test
      void addNewBudgetWithNotTakenName() {
        account.addBudget(new Budget("My First Budget"));
        assertTrue(account.addBudget(
            new Budget("My Second Budget")));
      }

      @Test
      void addNewBudgetWithTakenName() {
        account.addBudget(new Budget("My First Budget"));
        assertFalse(account.addBudget(
            new Budget("My First Budget")));
      }

      @Test
      void addExistingBudget() {
        Budget testBudget =
            new Budget("My Budget");
        account.addBudget(testBudget);
        assertFalse(account.addBudget(testBudget));
      }
    }

    @Test
    void removeBudgetPositiveTest () {
      account.addBudget(budget);
      account.removeBudget(budget);
      assertFalse(account.getBudgets().containsValue(budget));
    }

    @Nested
    class GenerateAccountNumberTest {

      @Test
      void accountNumberIsCorrectFormat() {
        assertEquals("ID-", account.getAccountNumber().substring(0, 3));
        assertEquals(17, account.getAccountNumber().length());
      }
    }
    @Test
    void toStringPositiveTest () {
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