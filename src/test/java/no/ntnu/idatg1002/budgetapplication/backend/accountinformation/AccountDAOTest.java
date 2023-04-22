package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {
  private AccountDAO accountDAO;
  private Account testAccount;

  @BeforeEach
  void setUp() {
    accountDAO = AccountDAO.getInstance();
    testAccount =
        new Account(
            "Test User", "test.user@example.com", "1234", SecurityQuestion.FAVORITE_FOOD, "test");
    accountDAO.add(testAccount);
  }

  @AfterEach
  void tearDown() {
    if (accountDAO.getAll().contains(testAccount)) {
      accountDAO.remove(testAccount);
    }
  }

  @Test
  void testAddAndFind() {
    Optional<Account> foundAccount = accountDAO.find(testAccount.getId());
    assertTrue(foundAccount.isPresent());
    assertEquals(testAccount, foundAccount.get());
  }

  @Test
  void testRemove() {
    accountDAO.remove(testAccount);
    Optional<Account> foundAccount = accountDAO.find(testAccount.getId());
    assertFalse(foundAccount.isPresent());
  }

  @Test
  void testUpdate() {
    testAccount.setName("Updated Test User");
    accountDAO.update(testAccount);
    Optional<Account> foundAccount = accountDAO.find(testAccount.getId());
    assertTrue(foundAccount.isPresent());
    assertEquals("Updated Test User", foundAccount.get().getName());
  }
}
