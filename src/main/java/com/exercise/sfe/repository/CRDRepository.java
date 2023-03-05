package com.exercise.sfe.repository;

import java.util.List;
import java.util.Optional;

/**
 * Base interface of a CRD repository for data layer.
 *
 * @param <T> Entity type of the data which is an [Entity]
 */
public interface CRDRepository<T> {

  /**
   * Finds all entities
   *
   * @return {@link List} of {@link T} objects
   */
  List<T> getAll();

  /**
   * Finds an entity by given id
   *
   * @param id Id of the entity to find
   * @return {@link Optional} of {@link T} object
   */
  Optional<T> getById(Long id);

  /**
   * Deletes entity by given id
   *
   * @param id Id of the entity to find
   * @return count of deleted entities
   */
  int deleteById(Long id);


  /**
   * Creates new entity
   *
   * @param t Entity to create
   * @return created entity
   */
  T create(T t);
}
