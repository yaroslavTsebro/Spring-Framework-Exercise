package com.exercise.sfe.repository;

public interface CRUDRepository<T> extends CRDRepository<T> {

  void update(T previousEnt, T wantedEnt);
}
