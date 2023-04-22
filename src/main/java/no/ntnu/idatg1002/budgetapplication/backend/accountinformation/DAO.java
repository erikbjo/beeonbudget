package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.Iterator;
import java.util.List;

public interface DAO extends Iterable<Account> {
  void addAccount(Account account);

  void removeAccount(Account account);

  void update(Account account);

  Iterator<Account> iterator();

  Account find(String accountNumber);

  List<Account> getAll();

  void printDetails();
}
