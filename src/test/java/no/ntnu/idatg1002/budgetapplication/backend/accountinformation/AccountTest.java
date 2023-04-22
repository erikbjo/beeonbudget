package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
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
    account = new Account("Test", "test@test.com", "1234", SecurityQuestion.CAR_BRAND, "BMW");

    budget = new Budget("Test budget");
    income =
        new Income(50, "Test income", RecurringType.NONRECURRING, IncomeCategory.PASSIVE);
    expense = new Expense(50, "Test expense", RecurringType.NONRECURRING, ExpenseCategory.HOUSING);
    budget.addBudgetIncome(income);
    budget.addBudgetExpenses(expense);

    savingsPlan = new SavingsPlan("Test savingsplan");
  }

  @AfterEach
  void tearDown() {
    for (Account a : AccountDAO.getInstance().getAll()) {
      a = null;
    }
  }

  @Nested
  class SetEmailTest {
    @Test
    void emailHasAtSignAndIsNotEmptyOrBlank() {
      assertDoesNotThrow(() -> account.setEmail("simon@gmail.com"));
      assertEquals("simon@gmail.com", account.getEmail());
    }

    @Test
    void emailHasNoAtSign() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setEmail("simon.gmail.com"));
      assertEquals("Email does not contain '@'.", thrown.getMessage());
      assertNotEquals("simon.gmail.com", account.getEmail());
    }

    @Test
    void emailIsEmpty() {
      Exception thrown = assertThrows(IllegalArgumentException.class, () -> account.setEmail(""));
      assertEquals("Email must not be empty or blank.", thrown.getMessage());
      assertNotEquals("", account.getEmail());
    }

    @Test
    void emailIsBlank() {
      Exception thrown = assertThrows(IllegalArgumentException.class, () -> account.setEmail(" "));
      assertEquals("Email must not be empty or blank.", thrown.getMessage());
      assertNotEquals(" ", account.getEmail());
    }

    @Test
    void emailAlreadyInUse() {
      Account testAccount = new Account("Erik", "simon@gmail.com", "4444", SecurityQuestion.FAVORITE_FOOD, "Pizza");
      AccountDAO.getInstance().add(testAccount);
          Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setEmail("simon@gmail.com"));
      assertEquals("Email already in use.", thrown.getMessage());
      AccountDAO.getInstance().remove(testAccount);
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
      assertDoesNotThrow(() -> account.setSecurityAnswer("Test answer"));
      assertEquals("Test answer", account.getSecurityAnswer());
    }
  }

  @Nested
  class setSecurityAnswerTest {
    @Test
    void securityAnswerIsNotEmptyOrBlank() {
      assertDoesNotThrow(() -> account.setSecurityAnswer("Test Answer"));
      assertEquals("Test Answer", account.getSecurityAnswer());
    }

    @Test
    void securityAnswerIsEmpty() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setSecurityAnswer(""));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals("", account.getSecurityAnswer());
    }

    @Test
    void securityAnswerIsBlank() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setSecurityAnswer(" "));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals(" ", account.getSecurityAnswer());
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

    @Nested
    class setNameTest {
      @Test
      void nameIsNotBlankOrEmpty() {
        assertDoesNotThrow(() -> account.setName("Test Name"));
        assertEquals("Test Name", account.getName());
      }

      @Test
      void nameIsEmpty() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> account.setName(""));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals("", account.getName());
      }

      @Test
      void nameIsBlank() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> account.setName(" "));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals(" ", account.getName());
      }
    }
  }

  @Nested
  class SetPinCodeTest {
    @Test
    void pinCodeHasFourDigits() {
      assertDoesNotThrow(() -> account.setPinCode("0001"));
      assertEquals("0001", account.getPinCode());
    }

    @Test
    void pinCodeHasLessThanFourDigits() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setPinCode("001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("001", account.getPinCode());
    }

    @Test
    void pinCodeHasMoreThanFourDigits() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setPinCode("00001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("00001", account.getPinCode());
    }

    @Test
    void pinCodeHasOnlyLetters() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setPinCode("code"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("code", account.getPinCode());
    }

    @Test
    void pinCodeHasLettersAndDigits() {
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.setPinCode("id09"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("id09", account.getPinCode());
    }
  }

  @Nested
  class addSavingsPlanTest {
    @Test
    void addNewSavingsPlanWithNotTakenName() {
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal");
      assertDoesNotThrow(() -> account.addSavingsPlan(testSavingsPlan));
      assertTrue(account.getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addNewSavingsPlanWithTakenName() {
      account.addSavingsPlan(new SavingsPlan("My goal"));
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal");
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> account.addSavingsPlan(testSavingsPlan));
      assertEquals("Savings plan goal name is taken.", thrown.getMessage());
      assertFalse(account.getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addExistingSavingsPlan() {
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal");
      account.addSavingsPlan(testSavingsPlan);
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> account.addSavingsPlan(testSavingsPlan));
      assertEquals("An instance of the savings plan already exists.", thrown.getMessage());
    }
  }

  @Test
  void removeSavingsPlanTest() {
    account.addSavingsPlan(savingsPlan);
    account.removeSavingsPlan(savingsPlan);
    assertFalse(account.getSavingsPlans().contains(savingsPlan));
  }

  @Nested
  class addBudgetTest {
    @Test
    void addNewBudgetWithNotTakenName() {
      Budget testBudget = new Budget("My budget");
      assertDoesNotThrow(() -> account.addBudget(testBudget));
      assertTrue(account.getBudgets().contains(testBudget));
    }

    @Test
    void addNewBudgetWithTakenName() {
      account.addBudget(new Budget("My budget"));
      Budget testBudget = new Budget("My budget");
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.addBudget(testBudget));
      assertEquals("Budget name is taken.", thrown.getMessage());
      assertFalse(account.getBudgets().contains(testBudget));
    }

    @Test
    void addExistingBudget() {
      Budget testBudget = new Budget("My budget");
      account.addBudget(testBudget);
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> account.addBudget(testBudget));
      assertEquals("An instance of the budget already exists.", thrown.getMessage());
    }
  }

  @Test
  void removeBudgetPositiveTest() {
    account.addBudget(budget);
    account.removeBudget(budget);
    assertFalse(account.getBudgets().contains(budget));
  }

  @Test
  void testThatSelectedBudgetIsInitializedCorrectly() {
    account.addBudget(budget);
    assertEquals(budget, account.getSelectedBudget());
  }

  @Test
  void testThatSelectedBudgetCanBeIncrementedAndLoops() {
    account.addBudget(budget);
    account.addBudget(new Budget("Test budget 2"));
    account.addBudget(new Budget("Test budget 3"));
    assertEquals("Test budget 3", account.getSelectedBudget().getBudgetName());
    //Selected loops
    account.selectNextBudget();
    assertEquals("Test budget", account.getSelectedBudget().getBudgetName());
    //Selected increments
    account.selectNextBudget();
    assertEquals("Test budget 2", account.getSelectedBudget().getBudgetName());
  }

  @Test
  void testThatSelectedSavingsPlanCanBeIncrementedAndLoops() {
    account.addSavingsPlan(savingsPlan);
    account.addSavingsPlan(new SavingsPlan("Test savingsplan 2"));
    account.addSavingsPlan(new SavingsPlan("Test savingsplan 3"));
    assertEquals("Test savingsplan 3", account.getSelectedSavingsPlan().getGoalName());
    //Selected loops
    account.selectNextSavingsPlan();
    assertEquals("Test savingsplan", account.getSelectedSavingsPlan().getGoalName());
    //Selected increments
    account.selectNextSavingsPlan();
    assertEquals("Test savingsplan 2", account.getSelectedSavingsPlan().getGoalName());
  }

  @Test
  void testThatSelectedBudgetCanBeDecreasedAndLoops() {
    account.addBudget(budget);
    account.addBudget(new Budget("Test budget 2"));
    assertEquals("Test budget 2", account.getSelectedBudget().getBudgetName());
    //Selected decreases
    account.selectPreviousBudget();
    assertEquals("Test budget", account.getSelectedBudget().getBudgetName());
    //Selected loops
    account.selectPreviousBudget();
    assertEquals("Test budget 2", account.getSelectedBudget().getBudgetName());
  }

  @Test
  void testThatSelectedSavingsPlanCanBeDecreasedAndLoops() {
    account.addSavingsPlan(savingsPlan);
    account.addSavingsPlan(new SavingsPlan("Test savingsplan 2"));
    assertEquals("Test savingsplan 2", account.getSelectedSavingsPlan().getGoalName());
    //Selected decreases
    account.selectPreviousSavingsPlan();
    assertEquals("Test savingsplan", account.getSelectedSavingsPlan().getGoalName());
    //Selected loops
    account.selectPreviousSavingsPlan();
    assertEquals("Test savingsplan 2", account.getSelectedSavingsPlan().getGoalName());
  }

  @Test
  void testThatSelectedSavingsPlanIsInitializedCorrectly() {
    account.addSavingsPlan(savingsPlan);
    assertEquals(savingsPlan, account.getSelectedSavingsPlan());
  }
}
