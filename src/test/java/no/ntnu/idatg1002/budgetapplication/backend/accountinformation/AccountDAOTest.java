package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import no.ntnu.idatg1002.budgetapplication.backend.SecurityQuestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {
  private AccountDAO accountDAO;
  private Account testAccount;

  @BeforeEach
  void setUp() {
    accountDAO = AccountDAO.getInstance();
    testAccount =
        new Account("Erik B", "erbj@ntnu.no", "1234", SecurityQuestion.FAVORITE_FOOD, "Pizza");
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
  void testAddAlreadyAddedAccount() {
    assertThrows(IllegalArgumentException.class, () -> accountDAO.add(testAccount));
  }

  @Test
  void testAddAccountWithTakenEmail() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            accountDAO.add(
                new Account(
                    "Test", "erbj@ntnu.no", "1234", SecurityQuestion.CAR_BRAND, "Porsche")));
  }

  @Test
  void testRemove() {
    accountDAO.remove(testAccount);
    Optional<Account> foundAccount = accountDAO.find(testAccount.getId());
    assertFalse(foundAccount.isPresent());
  }

  @Test
  void testUpdate() {
    testAccount.setName("Ozzy");
    accountDAO.update(testAccount);
    Optional<Account> foundAccount = accountDAO.find(testAccount.getId());
    assertTrue(foundAccount.isPresent());
    assertEquals("Ozzy", foundAccount.get().getName());
  }

  @Test
  void testLoginValid() {
    assertTrue(accountDAO.loginIsValid("erbj@ntnu.no", "1234"));
  }

  @Test
  void testLoginInvalid() {
    assertFalse(accountDAO.loginIsValid("erbj@ntnu.no", "0987"));
  }

  @Test
  void testThatIteratorReturnsAGoodIterator() {
    Iterator<Account> iterator = accountDAO.iterator();

    Account newAccount =
        new Account("Test", "test@test.com", "0000", SecurityQuestion.CAR_BRAND, "Ferrari");
    accountDAO.add(newAccount);

    assertTrue(iterator.hasNext());

    accountDAO.remove(newAccount);
  }
}
