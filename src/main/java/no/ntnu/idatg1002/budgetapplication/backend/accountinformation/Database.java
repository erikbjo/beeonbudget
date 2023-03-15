package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
  private static HashMap<String, Account> accounts = new HashMap<>();
  private static ArrayList<String> accountNumbers = new ArrayList<>();
  private static ArrayList<String> emails = new ArrayList<>();

  public static Map<String, Account> getAccounts() {
    return accounts;
  }

  public static List<String> getAccountNumbers() {
    return accountNumbers;
  }

  public static List<String> getEmails() {
    return emails;
  }


  public static boolean addAccount(Account account) {
    if (accounts.containsValue(account)) {
      return false;
    } else {
      accounts.put(account.getAccountNumber(), account);
      return true;
    }
  }
}
