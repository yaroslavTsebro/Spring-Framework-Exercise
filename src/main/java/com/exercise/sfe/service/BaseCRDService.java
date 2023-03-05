package com.exercise.sfe.service;

import java.util.List;

public interface BaseCRDService<T> {

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
   * @param t The entity for creation
   */
  T create(T t);
}
