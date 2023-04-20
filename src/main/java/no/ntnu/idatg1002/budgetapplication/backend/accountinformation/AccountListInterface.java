package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.Iterator;

public interface AccountListInterface extends Iterable<Account> {
  void addAccount(Account account);

  Iterator<Account> iterator();

  void printAccounts();
}
