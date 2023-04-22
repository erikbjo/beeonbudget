package no.ntnu.idatg1002.budgetapplication.backend.accountinformation;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object used to access data from a database.
 */
public interface DAO<T> extends Iterable<T> {

  /**
   * Adds a new instance of an entity to the database.
   *
   * @param t The entity to be added.
   */
  void add(T t);

  /**
   * Removes an instance of an entity from the database.
   *
   * @param t The entity to be removed.
   */
  void remove(T t);

  /**
   * Updates all fields of an entity in the database.
   *
   * @param t The entity to be updated.
   */
  void update(T t);

  /**
   * Returns an iterator of all entity instances in the database.
   *
   * @return An iterator of all entities in the database.
   */
  Iterator<T> iterator();

  /**
   * Finds a specific entity instance by using the entity's id.
   *
   * @param id The id as a String.
   * @return The entity instance found.
   */
  Optional<T> find(String id);

  /**
   * Returns a List of all entity instances in the database.
   *
   * @return All entity instances in the database as a List.
   */
  List<T> getAll();

  /**
   * Prints entity specific details of all the entity instances in the database.
   */
  void printAllDetails();
}
