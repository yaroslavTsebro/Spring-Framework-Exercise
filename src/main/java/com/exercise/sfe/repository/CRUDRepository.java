package com.exercise.sfe.repository;

/**
 * Base interface of a CRUD repository for data layer.
 *
 * @param <T> Entity type of the data which is an [Entity]
 */
public interface CRUDRepository<T> extends CRDRepository<T> {

  /**
   * Updates entity according to differences between created Entity and Entity to update
   *
   * @param previousEnt created Entity
   * @param wantedEnt   Entity to update
   */
  void update(T previousEnt, T wantedEnt);
}
