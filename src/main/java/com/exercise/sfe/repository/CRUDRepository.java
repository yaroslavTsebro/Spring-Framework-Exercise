package com.exercise.sfe.repository;


public interface CrudRepository<T> extends CrdRepository<T> {

  /**
   * Updates entity according to differences between created Entity and Entity to update
   *
   * @param t Entity to update
   */
  void update(T t);
}
