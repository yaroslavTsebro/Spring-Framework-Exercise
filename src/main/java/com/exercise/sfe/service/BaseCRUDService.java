package com.exercise.sfe.service;

public interface BaseCRUDService<T> extends BaseCRDService<T> {

  /**
   * Updates entity and return this entity
   *
   * @param t The entity to update
   * @return updated entity
   */
  T update(T t);

}

