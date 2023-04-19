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

  public AccountDAO() {
    this.emf = Persistence.createEntityManagerFactory("accountdb");
    this.em = this.emf.createEntityManager();
  }

  @Override
  public void addAccount(Account account) {
    this.em.getTransaction().begin();
    this.em.persist(account);
    this.em.getTransaction().commit();
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

  @Override
  public void printAccounts() {
    List<Account> accountList = getAllAccounts();

    for (Account account : accountList) {
      System.out.println("Account Details : "
          + " " + account.getId()
          + " " + account.getName()
          + " " + account.getEmail());
    }
  }

  void close() {
    this.em.close();
    this.emf.close();
  }
}
