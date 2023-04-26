package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.Expense;
import no.ntnu.idatg1002.budgetapplication.backend.ExpenseCategory;
import no.ntnu.idatg1002.budgetapplication.backend.Income;
import no.ntnu.idatg1002.budgetapplication.backend.IncomeCategory;
import no.ntnu.idatg1002.budgetapplication.backend.RecurringType;
import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import org.junit.jupiter.api.*;

class AccountTest {
  private final ArrayList<String> testEmails =
      new ArrayList<>(List.of(new String[] {"erbj@ntnu.no.test", "simonhou@ntnu.no.test"}));
  private AccountDAO accountDAO;
  private SessionAccount sessionAccount;
  private Account erikAccount;
  private Account simonAccount;
  private Budget budget;
  private Income income;
  private Expense expense;
  private SavingsPlan savingsPlan;

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

  @Test
  void removeSavingsPlanTest() {
    sessionAccount.getAccount().addSavingsPlan(savingsPlan);
    sessionAccount.getAccount().removeSavingsPlan(savingsPlan);
    assertFalse(sessionAccount.getAccount().getSavingsPlans().contains(savingsPlan));
  }

  @Test
  void removeBudgetPositiveTest() {
    sessionAccount.getAccount().removeBudget(budget);
    assertFalse(sessionAccount.getAccount().getBudgets().contains(budget));
  }

  @Test
  void testThatSelectedBudgetIsInitializedCorrectly() {
    Account account = sessionAccount.getAccount();
    assertThrows(
        IllegalArgumentException.class, () -> account.addBudget(budget));
    assertEquals(budget, account.getSelectedBudget());
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
    sessionAccount
        .getAccount()
        .addSavingsPlan(
            new SavingsPlan("Test savingsplan 2", 100, LocalDate.now(), LocalDate.now()));
    sessionAccount
        .getAccount()
        .addSavingsPlan(
            new SavingsPlan("Test savingsplan 3", 100, LocalDate.now(), LocalDate.now()));
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
    sessionAccount
        .getAccount()
        .addSavingsPlan(
            new SavingsPlan("Test savingsplan 2", 100, LocalDate.now(), LocalDate.now()));
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
    Account account = sessionAccount.getAccount();
    assertNull(account.getCurrentSavingsPlanIndex());
    assertThrows(
        IndexOutOfBoundsException.class,
            account::getSelectedSavingsPlan);
  }

  @Test
  void testThatInitSelectedBudgetsBecomesNullWhenTheresNoBudgets() {
    sessionAccount.getAccount().removeBudget(budget);

    sessionAccount.getAccount().initializeSelectedBudget();

    Account account = sessionAccount.getAccount();

    assertNull(account.getCurrentBudgetIndex());
    assertThrows(
        IndexOutOfBoundsException.class, account::getSelectedBudget);
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
      Account account = sessionAccount.getAccount();
      assertThrows(
          IllegalArgumentException.class,
          () -> account.setEmail("erik.gmail.com"));
      assertNotEquals("erik.gmail.com", account.getEmail());
    }

    @Test
    void emailIsEmpty() {
      Account account = sessionAccount.getAccount();
      assertThrows(IllegalArgumentException.class, () -> account.setEmail(""));
      assertNotEquals("", sessionAccount.getAccount().getEmail());
    }

    @Test
    void emailIsBlank() {
      Account account = sessionAccount.getAccount();
      assertThrows(IllegalArgumentException.class, () -> account.setEmail(" "));
      assertNotEquals(" ", account.getEmail());
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
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.setSecurityAnswer(""));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals("", account.getSecurityAnswer());
    }

    @Test
    void securityAnswerIsBlank() {
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.setSecurityAnswer(" "));
      assertEquals("Security answer must not be empty or blank.", thrown.getMessage());
      assertNotEquals(" ", account.getSecurityAnswer());
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
        Account account = sessionAccount.getAccount();
        Exception thrown =
            assertThrows(
                IllegalArgumentException.class, () -> account.setName(""));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals("", account.getName());
      }

      @Test
      void nameIsBlank() {
        Account account = sessionAccount.getAccount();
        Exception thrown =
            assertThrows(
                IllegalArgumentException.class, () -> account.setName(" "));
        assertEquals("Account name must not be empty or blank.", thrown.getMessage());
        assertNotEquals(" ", account.getName());
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
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> account.setPinCode("001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("001", account.getPinCode());
    }

    @Test
    void pinCodeHasMoreThanFourDigits() {
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.setPinCode("00001"));
      assertEquals("Pin code must consist of 4 digits.", thrown.getMessage());
      assertNotEquals("00001", account.getPinCode());
    }

    @Test
    void pinCodeHasOnlyLetters() {
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> account.setPinCode("code"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("code", account.getPinCode());
    }

    @Test
    void pinCodeHasLettersAndDigits() {
      Account account = sessionAccount.getAccount();
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class, () -> account.setPinCode("id09"));
      assertEquals("Pin code must only consist of numbers.", thrown.getMessage());
      assertNotEquals("id09", account.getPinCode());
    }
  }

  @Nested
  class addSavingsPlanTest {
    @Test
    void addNewSavingsPlanWithNotTakenName() {
      SavingsPlan testSavingsPlan =
          new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      assertDoesNotThrow(() -> sessionAccount.getAccount().addSavingsPlan(testSavingsPlan));
      assertTrue(sessionAccount.getAccount().getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addNewSavingsPlanWithTakenName() {
      Account account = sessionAccount.getAccount();
      sessionAccount
          .getAccount()
          .addSavingsPlan(new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now()));
      SavingsPlan testSavingsPlan =
          new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.addSavingsPlan(testSavingsPlan));
      assertEquals("Savings plan goal name is taken.", thrown.getMessage());
      assertFalse(account.getSavingsPlans().contains(testSavingsPlan));
    }

    @Test
    void addExistingSavingsPlan() {
      Account account = sessionAccount.getAccount();
      SavingsPlan testSavingsPlan =
          new SavingsPlan("My goal", 100, LocalDate.now(), LocalDate.now());
      account.addSavingsPlan(testSavingsPlan);
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.addSavingsPlan(testSavingsPlan));
      assertEquals("An instance of the savings plan already exists.", thrown.getMessage());
    }
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
      Account account = sessionAccount.getAccount();
      account.addBudget(testBudget);
      Exception thrown =
          assertThrows(
              IllegalArgumentException.class,
              () -> account.addBudget(testBudget));
      assertEquals("An instance of the budget already exists.", thrown.getMessage());
    }
  }
}
