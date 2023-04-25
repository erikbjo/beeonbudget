package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

class AccountTest {
  private AccountDAO accountDAO;
  private SessionAccount sessionAccount;
  private Account erikAccount;
  private Account simonAccount;
  private Budget budget;
  private Income income;
  private Expense expense;
  private SavingsPlan savingsPlan;

  private final ArrayList<String> testEmails =
      new ArrayList<>(List.of(new String[] {"erbj@ntnu.no.test", "simonhou@ntnu.no.test"}));

  @BeforeEach
  void setUp() {
    accountDAO = new AccountDAO();
    sessionAccount = SessionAccount.getInstance();

    removeTestAccounts();

    erikAccount =
        new Account(
            "Erik Bjørnsen", testEmails.get(0), "1234", SecurityQuestion.FAVORITE_FOOD, "Pizza");
    simonAccount =
        new Account("Simon Houmb", testEmails.get(1), "1234", SecurityQuestion.CAR_BRAND, "BMW");

    accountDAO.add(erikAccount);
    accountDAO.add(simonAccount);

    sessionAccount.setAccount(erikAccount);

    budget = new Budget("Test budget");
    income = new Income(50, "Test income", RecurringType.NONRECURRING, IncomeCategory.PASSIVE);
    expense = new Expense(50, "Test expense", RecurringType.NONRECURRING, ExpenseCategory.HOUSING);

    budget.addBudgetIncome(income);
    budget.addBudgetExpenses(expense);

    sessionAccount.getAccount().addBudget(budget);

    savingsPlan = new SavingsPlan("Test savingsplan", 100, LocalDate.now(), LocalDate.now());
  }

  @AfterEach
  void tearDown() {
    accountDAO.close();
    accountDAO = new AccountDAO();
    removeTestAccounts();
    accountDAO.close();
  }

  private void removeTestAccounts() {
    if (accountDAO.getAllEmails().contains(testEmails.get(0))
        || accountDAO.getAllEmails().contains(testEmails.get(1))) {
      accountDAO.remove(accountDAO.getAccountByEmail(testEmails.get(0)));
      accountDAO.remove(accountDAO.getAccountByEmail(testEmails.get(1)));
    }
  }

  @Nested
  class SetEmailTest {
    @Test
    void emailHasAtSignAndIsNotEmptyOrBlank() {
      assertDoesNotThrow(() -> sessionAccount.getAccount().setEmail("erikb@gmail.com"));
      assertEquals("erikb@gmail.com", sessionAccount.getAccount().getEmail());
    }

    @Test
    void emailHasNoAtSign() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().setEmail("erik.gmail.com"));
      assertEquals("Not a valid e-mail address.", thrown.getMessage());
      assertNotEquals("erik.gmail.com", sessionAccount.getAccount().getEmail());
    }

    @Test
    void emailIsEmpty() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> sessionAccount.getAccount().setEmail(""));
      assertEquals("Not a valid e-mail address.", thrown.getMessage());
      assertNotEquals("", sessionAccount.getAccount().getEmail());
    }

    @Test
    void emailIsBlank() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> sessionAccount.getAccount().setEmail(" "));
      assertEquals("Not a valid e-mail address.", thrown.getMessage());
      assertNotEquals(" ", sessionAccount.getAccount().getEmail());
    }

    @Test
    void emailAlreadyInUse() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> erikAccount.setEmail(testEmails.get(1)));
      assertEquals("Email already in use.", thrown.getMessage());
    }
  }

  @Nested
  class securityQuestionTests {
    @Test
    void getAndSetSecurityQuestionPositive() {
      sessionAccount.getAccount().setSecurityQuestion(SecurityQuestion.FIRST_PET);
      assertEquals(SecurityQuestion.FIRST_PET, sessionAccount.getAccount().getSecurityQuestion());
    }

    @Test
    void getAndSetSecurityAnswerPositive() {
      assertDoesNotThrow(() -> sessionAccount.getAccount().setSecurityAnswer("Test answer"));
      assertEquals("Test answer", sessionAccount.getAccount().getSecurityAnswer());
    }
  }

  @Nested
  class setSecurityAnswerTest {
    @Test
    void securityAnswerIsNotEmptyOrBlank() {
      assertDoesNotThrow(() -> sessionAccount.getAccount().setSecurityAnswer("Test Answer"));
      assertEquals("Test Answer", sessionAccount.getAccount().getSecurityAnswer());
    }

    @Test
    void securityAnswerIsEmpty() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().setSecurityAnswer(""));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals("", sessionAccount.getAccount().getSecurityAnswer());
    }

    @Test
    void securityAnswerIsBlank() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().setSecurityAnswer(" "));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals(" ", sessionAccount.getAccount().getSecurityAnswer());
    }
  }

  @Nested
  class simpleGettersAndSettersTest {
    @Test
    void getNameReturnsCorrectName() {
      assertEquals("Erik Bjørnsen", sessionAccount.getAccount().getName());
    }

    @Test
    void getEmailReturnsCorrectEmail() {
      assertEquals(testEmails.get(0), sessionAccount.getAccount().getEmail());
    }

    @Nested
    class setNameTest {
      @Test
      void nameIsNotBlankOrEmpty() {
        assertDoesNotThrow(() -> sessionAccount.getAccount().setName("Test Name"));
        assertEquals("Test Name", sessionAccount.getAccount().getName());
      }

      @Test
      void nameIsEmpty() {
        Exception thrown =
            assertThrows(
                IllegalArgumentException.class, () -> sessionAccount.getAccount().setName(""));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals("", sessionAccount.getAccount().getName());
      }

      @Test
      void nameIsBlank() {
        Exception thrown =
            assertThrows(
                IllegalArgumentException.class, () -> sessionAccount.getAccount().setName(" "));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals(" ", sessionAccount.getAccount().getName());
      }
    }
  }

  @Nested
  class SetPinCodeTest {
    @Test
    void pinCodeHasFourDigits() {
      assertDoesNotThrow(() -> sessionAccount.getAccount().setPinCode("0001"));
      assertEquals("0001", sessionAccount.getAccount().getPinCode());
    }

    @Test
    void pinCodeHasLessThanFourDigits() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> sessionAccount.getAccount().setPinCode("001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("001", sessionAccount.getAccount().getPinCode());
    }

    @Test
    void pinCodeHasMoreThanFourDigits() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().setPinCode("00001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("00001", sessionAccount.getAccount().getPinCode());
    }

    @Test
    void pinCodeHasOnlyLetters() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> sessionAccount.getAccount().setPinCode("code"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("code", sessionAccount.getAccount().getPinCode());
    }

    @Test
    void pinCodeHasLettersAndDigits() {
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> sessionAccount.getAccount().setPinCode("id09"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("id09", sessionAccount.getAccount().getPinCode());
    }
  }

  @Nested
  class addSavingsPlanTest {
    @Test
    void addNewSavingsPlanWithNotTakenName() {
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      assertDoesNotThrow(() -> sessionAccount.getAccount().addSavingsPlan(testSavingsPlan));
      assertTrue(sessionAccount.getAccount().getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addNewSavingsPlanWithTakenName() {
      sessionAccount.getAccount().addSavingsPlan(new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now()));
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().addSavingsPlan(testSavingsPlan));
      assertEquals("Savings plan goal name is taken.", thrown.getMessage());
      assertFalse(sessionAccount.getAccount().getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addExistingSavingsPlan() {
      SavingsPlan testSavingsPlan = new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      sessionAccount.getAccount().addSavingsPlan(testSavingsPlan);
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().addSavingsPlan(testSavingsPlan));
      assertEquals("An instance of the savings plan already exists.", thrown.getMessage());
    }
  }

  @Test
  void removeSavingsPlanTest() {
    sessionAccount.getAccount().addSavingsPlan(savingsPlan);
    sessionAccount.getAccount().removeSavingsPlan(savingsPlan);
    assertFalse(sessionAccount.getAccount().getSavingsPlans().contains(savingsPlan));
  }

  @Nested
  class addBudgetTest {
    @Test
    void addNewBudgetWithNotTakenName() {
      Budget testBudget = new Budget("My budget");
      assertDoesNotThrow(() -> sessionAccount.getAccount().addBudget(testBudget));
      assertTrue(sessionAccount.getAccount().getBudgets().contains(testBudget));
    }

    @Test
    void addNewBudgetWithTakenName() {
      Budget testBudget = new Budget("My budget");
      sessionAccount.getAccount().addBudget(testBudget);
      Exception thrown =
          assertThrows(IllegalArgumentException.class, () -> new Budget("My budget"));
      assertEquals("Budget name is taken.", thrown.getMessage());
      sessionAccount.getAccount().removeBudget(testBudget);
    }

    @Test
    void addExistingBudget() {
      Budget testBudget = new Budget("My budget");
      sessionAccount.getAccount().addBudget(testBudget);
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> sessionAccount.getAccount().addBudget(testBudget));
      assertEquals("An instance of the budget already exists.", thrown.getMessage());
    }
  }

  @Test
  void removeBudgetPositiveTest() {
    sessionAccount.getAccount().removeBudget(budget);
    assertFalse(sessionAccount.getAccount().getBudgets().contains(budget));
  }

  @Test
  void testThatSelectedBudgetIsInitializedCorrectly() {
    assertThrows(
        IllegalArgumentException.class, () -> sessionAccount.getAccount().addBudget(budget));
    assertEquals(budget, sessionAccount.getAccount().getSelectedBudget());
  }

  @Test
  void testThatSelectedBudgetCanBeIncrementedAndLoops() {
    sessionAccount.getAccount().addBudget(new Budget("Test budget 2"));
    sessionAccount.getAccount().addBudget(new Budget("Test budget 3"));
    assertEquals("Test budget 3", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
    // Selected loops
    sessionAccount.getAccount().selectNextBudget();
    assertEquals("Test budget", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
    // Selected increments
    sessionAccount.getAccount().selectNextBudget();
    assertEquals("Test budget 2", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
  }

  @Test
  void testThatSelectedSavingsPlanCanBeIncrementedAndLoops() {
    sessionAccount.getAccount().addSavingsPlan(savingsPlan);
    sessionAccount.getAccount().addSavingsPlan(new SavingsPlan("Test savingsplan 2", 100, LocalDate.now(), LocalDate.now()));
    sessionAccount.getAccount().addSavingsPlan(new SavingsPlan("Test savingsplan 3", 100, LocalDate.now(), LocalDate.now()));
    assertEquals("Test savingsplan 3", erikAccount.getSelectedSavingsPlan().getGoalName());
    // Selected loops
    sessionAccount.getAccount().selectNextSavingsPlan();
    assertEquals(
        "Test savingsplan", sessionAccount.getAccount().getSelectedSavingsPlan().getGoalName());
    // Selected increments
    sessionAccount.getAccount().selectNextSavingsPlan();
    assertEquals(
        "Test savingsplan 2", sessionAccount.getAccount().getSelectedSavingsPlan().getGoalName());
  }

  @Test
  void testThatSelectedBudgetCanBeDecreasedAndLoops() {
    sessionAccount.getAccount().addBudget(new Budget("Test budget 2"));
    assertEquals("Test budget 2", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
    // Selected decreases
    sessionAccount.getAccount().selectPreviousBudget();
    assertEquals("Test budget", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
    // Selected loops
    sessionAccount.getAccount().selectPreviousBudget();
    assertEquals("Test budget 2", sessionAccount.getAccount().getSelectedBudget().getBudgetName());
  }

  @Test
  void testThatSelectedSavingsPlanCanBeDecreasedAndLoops() {
    sessionAccount.getAccount().addSavingsPlan(savingsPlan);
    sessionAccount.getAccount().addSavingsPlan(new SavingsPlan("Test savingsplan 2", 100, LocalDate.now(), LocalDate.now()));
    assertEquals(
        "Test savingsplan 2", sessionAccount.getAccount().getSelectedSavingsPlan().getGoalName());
    // Selected decreases
    sessionAccount.getAccount().selectPreviousSavingsPlan();
    assertEquals(
        "Test savingsplan", sessionAccount.getAccount().getSelectedSavingsPlan().getGoalName());
    // Selected loops
    sessionAccount.getAccount().selectPreviousSavingsPlan();
    assertEquals(
        "Test savingsplan 2", sessionAccount.getAccount().getSelectedSavingsPlan().getGoalName());
  }

  @Test
  void testThatSelectedSavingsPlanIsInitializedCorrectly() {
    sessionAccount.getAccount().addSavingsPlan(savingsPlan);
    assertEquals(savingsPlan, sessionAccount.getAccount().getSelectedSavingsPlan());
  }

  @Test
  void testThatInitSelectedSavingsPlanBecomesNullWhenTheresNoSavingsPlans() {
    sessionAccount.getAccount().initializeSelectedSavingsPlan();
    assertNull(sessionAccount.getAccount().getCurrentSavingsPlanIndex());
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> sessionAccount.getAccount().getSelectedSavingsPlan());
  }

  @Test
  void testThatInitSelectedBudgetsBecomesNullWhenTheresNoBudgets() {
    sessionAccount.getAccount().removeBudget(budget);

    sessionAccount.getAccount().initializeSelectedBudget();

    assertNull(sessionAccount.getAccount().getCurrentBudgetIndex());
    assertThrows(
        IndexOutOfBoundsException.class, () -> sessionAccount.getAccount().getSelectedBudget());
  }
}
