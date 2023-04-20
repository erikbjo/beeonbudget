package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AccountList implements AccountListInterface {
  HashMap<String, Account> accounts;
  ArrayList<String> emails;

  private static Account currentAccount;


  /**
   * Adds a new account to the database. If the account already exists or
   * an account with the same AccountNumber exists then it will not be added.
   *
   * @param account the account to be added to the database.
   * @throws IllegalArgumentException if an instance of the account already exists or
   *     if the account number of the account is already taken.
   */
  @Override
  public void addAccount(Account account) throws IllegalArgumentException {
    if (accounts.containsValue(account)) {
      throw new IllegalArgumentException("Instance of account already exists.");
    } else if (accounts.containsKey(account.getId())) {
      throw new IllegalArgumentException("Account with the same account number already exists.");
    } else {
      accounts.put(account.getId(), account);
      emails.add(account.getEmail());
    }
  }

  @Override
  public void removeAccount(Account account) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns all emails registered to accounts in the database.
   *
   * @return all emails registered to accounts in the database as a List.
   */
  public List<String> getEmails() {
    return emails;
  }

  @Override
  public Iterator<Account> iterator() {
    return this.accounts.values().iterator();
  }

  @Override
  public void printAccounts() {
    throw new UnsupportedOperationException();
  }

  public static Account getCurrentAccount() {
    return currentAccount;
  }

  public static void setCurrentAccount(Account currentAccount) {
    AccountList.currentAccount = currentAccount;
  }
}
