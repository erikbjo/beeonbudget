package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;

public class AccountDAO implements AccountListInterface {
  private final EntityManagerFactory emf;
  private EntityManager em;

  private static final AccountDAO instance = new AccountDAO();

  public AccountDAO() {
    this.emf = Persistence.createEntityManagerFactory("accountdb");
    this.em = this.emf.createEntityManager();
  }

  public static AccountDAO getInstance() {
    return instance;
  }

  @Override
  public void addAccount(Account account) {
    if (AccountDAO.getInstance().getAllAccounts().contains(account)) {
      throw new IllegalArgumentException("Instance of account already exists in the database.");
    } else if (AccountDAO.getInstance().getAllAccountIds().contains(account.getId())) {
      throw new IllegalArgumentException("Account with the same account number already exists in the database.");
    } else if (AccountDAO.getInstance().getAllEmails().contains(account.getEmail())) {
      throw new IllegalArgumentException("Email already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(account);
      this.em.getTransaction().commit();
    }
  }

  @Override
  public Iterator<Account> iterator() {
    TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account a", Account.class);
    return query.getResultList().iterator();
  }

  public Account findAccount(String accountNumber) {
    return em.find(Account.class, accountNumber);
  }

  public List<Account> getAllAccounts() {
    return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
  }

  public List<String> getAllEmails() {
    return em.createQuery("SELECT a.email FROM Account a", String.class).getResultList();
  }

  public List<String> getAllAccountIds() {
    return em.createQuery("SELECT a.id FROM Account a", String.class).getResultList();
  }

  public Account getAccountByEmail(String email) {
    return em.createQuery("SELECT a FROM Account a WHERE a.email LIKE '" + email + "'",
        Account.class).getSingleResult();
  }

  public boolean loginIsValid(String email, String pinCode) {
    List<String> allEmails = getAllEmails();
    return allEmails.contains(email) && getAccountByEmail(email).getPinCode().equals(pinCode);
  }

  @Override
  public void printAccounts() {
    List<Account> accountList = getAllAccounts();
    for (Account account : accountList) {
      System.out.println("Account Details"
          + " :: " + account.getId()
          + " :: " + account.getName()
          + " :: " + account.getEmail());
    }
  }

  void close() {
    this.em.close();
    this.emf.close();
  }
}
