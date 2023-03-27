package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.Budget;
import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import no.ntnu.idatg1002.budgetapplication.backend.savings.SavingsPlan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AccountTest {
Account account;

  @BeforeEach
  void setUp() {
    account = new Account("Test", "test@test.com", "1234",
        SecurityQuestion.CAR_BRAND, "BMW");
  }

  @AfterEach
  void clearAccount() {

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

  @Nested
  class GenerateAccountNumberTest {
    @Test
    void accountNumberIsCorrectFormat() {
      assertEquals("ID-", account.getAccountNumber().substring(0, 3));
      assertEquals(17, account.getAccountNumber().length());
    }
  }
}