package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

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
  class GenerateAccountNumberTest {
    @Test
    void accountNumberIsCorrectFormat() {
      assertEquals("ID-", account.getAccountNumber().substring(0, 3));
      assertEquals(17, account.getAccountNumber().length());
    }
  }
}