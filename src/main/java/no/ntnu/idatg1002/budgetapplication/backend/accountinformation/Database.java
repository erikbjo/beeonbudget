package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the database that hold all the accounts added to the application.
 */
public class Database {
  private static HashMap<String, Account> accounts = new HashMap<>();
  private static ArrayList<String> emails = new ArrayList<>();

  /**
   * Returns a Map of all the accounts in the database.
   *
   * @return all accounts in the database as a Map.
   */
  public static Map<String, Account> getAccounts() {
    return accounts;
  }


  /**
   * Returns all emails registered to accounts in the database.
   *
   * @return all emails registered to accounts in the database as a List.
   */
  public static List<String> getEmails() {
    return emails;
  }

  /**
   * Adds a new account to the database. If the account already exists or
   * an account with the same AccountNumber exists then it will not be added.
   *
   * @param account the account to be added to the database.
   * @return whether the account was added or not.
   */
  public static boolean addAccount(Account account) {
    if (accounts.containsValue(account) || accounts.containsKey(account.getAccountNumber())) {
      return false;
    } else {
      accounts.put(account.getAccountNumber(), account);
      return true;
    }
  }
}
