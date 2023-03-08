package com.exercise.sfe.repository;

/**
 * Base interface of a CRUD repository for data layer.
 *
 * @param <T> Entity type of the data which is an [Entity]
 */
public interface CrudRepository<T> extends CrdRepository<T> {

  /**
   * Updates entity according to differences between created Entity and Entity to update
   *
   * @param t   Entity to update
   */
  void update(T t);
}
