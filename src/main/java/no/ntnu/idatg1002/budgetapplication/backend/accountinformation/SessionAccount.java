package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

/**
 * Singleton class to hold the current session's account logged in to the application.
 */
public class SessionAccount {
  private Account account;
  private static final SessionAccount instance = new SessionAccount();


  private SessionAccount() {}

  /**
   * Returns an instance of the SessionAccount.
   *
   * @return the instance of SessionAccount.
   */
  public static SessionAccount getInstance() {
    return instance;
  }

  /**
   * Returns the Account used in this session.
   *
   * @return The Account.
   */
  public Account getAccount() {
    return account;
  }

  /**
   * Sets the Account used in this session. Could for example be used when logging in.
   *
   * @param account The Account to set.
   */
  public void setAccount(Account account) {
    this.account = account;
  }

  /**
   * Clears the Account used in the session. Could for example be used when logging out.
   *
   */
  public void clearAccount() {
    this.account = null;
  }
}
