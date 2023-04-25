package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to account data stored in the database. This class implements the DAO interface
 * for the Account class. It is a singleton class with instance methods for performing create, read,
 * update and delete operations.
 *
 * @author Simon Houmb
 */
public class AccountDAO implements DAO<Account> {
  private final EntityManagerFactory emf;
  private EntityManager em;

  private static final AccountDAO instance = new AccountDAO();

  /** Constructs an AccountDAO instance, initializing the EntityManagerFactory and EntityManager. */
  public AccountDAO() {
    this.emf = Persistence.createEntityManagerFactory("accountdb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the AccountDAO instance.
   *
   * @return the AccountDAO instance.
   */
  public static AccountDAO getInstance() {
    return instance;
  }

  /**
   * Adds a new account to the database if it does not already exist.
   *
   * @param account the account to be added.
   * @throws IllegalArgumentException if the account already exists.
   * @throws IllegalArgumentException if an account with the same account number exists.
   * @throws IllegalArgumentException if an account with the same email exists.
   */
  @Override
  public void add(Account account) {
    if (AccountDAO.getInstance().getAll().contains(account)) {
      throw new IllegalArgumentException("Instance of account already exists in the database.");
    } else if (AccountDAO.getInstance().getAllAccountIds().contains(account.getId())) {
      throw new IllegalArgumentException(
          "Account with the same account number already exists in the database.");
    } else if (AccountDAO.getInstance().getAllEmails().contains(account.getEmail())) {
      throw new IllegalArgumentException("Email already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(account);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes an account from the database.
   *
   * @param account the account to be removed.
   */
  public void remove(Account account) {
    Account foundAccount = em.find(Account.class, account.getId());
    em.getTransaction().begin();
    em.remove(foundAccount);
    em.getTransaction().commit();
  }

  /**
   * Updates an existing account in the database.
   *
   * @param account the account to be updated.
   */
  @Override
  public void update(Account account) {
    em.getTransaction().begin();
    em.merge(account);
    em.flush();
    em.getTransaction().commit();
    System.out.println("db updated...");
  }

  /**
   * Returns an iterator over all accounts in the database.
   *
   * @return an iterator over accounts.
   */
  @Override
  public Iterator<Account> iterator() {
    TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account a", Account.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds an account by its id.
   *
   * @param id the account id to search for.
   * @return an Optional containing the account if found, otherwise empty.
   */
  @Override
  public Optional<Account> find(String id) {
    return Optional.ofNullable(em.find(Account.class, id));
  }

  /**
   * Retrieves all accounts in the database.
   *
   * @return a list of all accounts.
   */
  @Override
  public List<Account> getAll() {
    return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
  }

  /**
   * Returns all account emails in the database.
   *
   * @return All account emails as a List.
   */
  public List<String> getAllEmails() {
    return em.createQuery("SELECT a.email FROM Account a", String.class).getResultList();
  }

  /**
   * Returns all account ids in the database.
   *
   * @return All account ids in the database as a List.
   */
  public List<String> getAllAccountIds() {
    return em.createQuery("SELECT a.id FROM Account a", String.class).getResultList();
  }

  /**
   * Finds and returns an account from the database by matching email.
   *
   * @param email The email to find the account by as a String.
   * @return The account found.
   */
  public Account getAccountByEmail(String email) {
    return em.createQuery(
            "SELECT a FROM Account a WHERE a.email LIKE '" + email + "'", Account.class)
        .getSingleResult();
  }

  /**
   * Authenticates the login information.
   *
   * @param email The email to authenticate.
   * @param pinCode The pin code to authenticate.
   * @return Whether the login information is valid or not as a boolean.
   */
  public boolean loginIsValid(String email, String pinCode) {
    List<String> allEmails = getAllEmails();
    return allEmails.contains(email) && getAccountByEmail(email).getPinCode().equals(pinCode);
  }

  /** Prints details for all accounts in the database. */
  @Override
  public void printAllDetails() {
    List<Account> accountList = getAll();
    for (Account account : accountList) {
      System.out.println(
          "Account Details"
              + " :: "
              + account.getId()
              + " :: "
              + account.getName()
              + " :: "
              + account.getEmail());
    }
  }

  /** Closes the EntityManager and EntityManagerFactory. */
  @Override
  public void close() {
    if (em.isOpen()) {
      this.em.close();
    }
    if (emf.isOpen()) {
      this.emf.close();
    }
  }
}
