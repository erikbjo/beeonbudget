package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import org.junit.jupiter.api.Test;

class SessionAccountTest {
  private final SessionAccount sessionAccount = SessionAccount.getInstance();
  private final Account testAccount =
      new Account("Erik B", "erbj@ntnu.no", "1234", SecurityQuestion.FAVORITE_FOOD, "Pizza");

  @Test
  void testGetInstance() {
    assertNotNull(sessionAccount);
  }

  @Test
  void testSetAndGetAccount() {
    sessionAccount.setAccount(testAccount);
    Account retrievedAccount = sessionAccount.getAccount();
    assertEquals(testAccount, retrievedAccount);
  }

  @Test
  void testSetAndGetAccountNegative() {
    sessionAccount.setAccount(testAccount);
    Account badAccount =
        new Account("Bad Acc", "bad@account.com", "1337", SecurityQuestion.FATHER_BORN, "1337");
    Account retrievedAccount = sessionAccount.getAccount();
    assertNotEquals(badAccount, retrievedAccount);
  }

  @Test
  void testClearAccount() {
    sessionAccount.setAccount(testAccount);
    sessionAccount.clearAccount();
    assertNull(sessionAccount.getAccount());
  }
}
