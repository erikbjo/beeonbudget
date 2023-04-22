package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.Iterator;

public interface DAO extends Iterable<Account> {
  void addAccount(Account account);

  void removeAccount(Account account);

  Iterator<Account> iterator();

  void printAccounts();
}
