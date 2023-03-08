package com.exercise.sfe.service;

public interface CrudService<T, K> extends CrdService<T, K> {

  /**
   * Updates entity and return this entity
   *
   * @param k The dto of entity to update
   * @return updated entity
   */
  T update(K k, Long id);

}

