package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;

public class AccountDAO implements DAO {
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
    if (AccountDAO.getInstance().getAll().contains(account)) {
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
  public void removeAccount(Account account) {
    Account foundAccount = em.find(Account.class, account.getId());
    em.getTransaction().begin();
    em.remove(foundAccount);
    em.getTransaction().commit();
  }

  @Override
  public void update(Account account) {
    em.getTransaction().begin();
    em.merge(account);
    em.flush();
    em.getTransaction().commit();
  }

  @Override
  public Iterator<Account> iterator() {
    TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account a", Account.class);
    return query.getResultList().iterator();
  }

  @Override
  public Account find(String accountNumber) {
    return em.find(Account.class, accountNumber);
  }

  @Override
  public List<Account> getAll() {
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
  public void printDetails() {
    List<Account> accountList = getAll();
    for (Account account : accountList) {
      System.out.println("Account Details"
          + " :: " + account.getId()
          + " :: " + account.getName()
          + " :: " + account.getEmail());
    }
  }


  public void close() {
    if (em.isOpen()) {
      this.em.close();
      System.out.println("EntityManager was closed.");
    }
    if (emf.isOpen()) {
      this.emf.close();
      System.out.println("EntityManagerFactory was closed.");
    }
  }
}
