package com.exercise.sfe.service;

import java.util.List;

public interface CrdService<T, K> {

  /**
   * Finds all entities
   *
   * @return {@link List} of entities
   */
  List<T> getAll();

  /**
   * Finds entity by given id
   *
   * @param id The id of entity
   * @return entity
   */
  T getById(Long id);

  /**
   * Deletes entity by id
   *
   * @param id The id of entity
   */
  void deleteById(Long id);

  /**
   * Creates entity by instance
   *
   * @param k The dto of entity for creation
   */
  T create(K k);
}
