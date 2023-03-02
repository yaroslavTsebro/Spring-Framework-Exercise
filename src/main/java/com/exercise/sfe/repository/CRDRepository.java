package com.exercise.sfe.repository;

import java.util.List;
import java.util.Optional;

public interface CRDRepository<T> {

  List<T> getAll();

  Optional<T> getById(Long id);

  int deleteById(Long id);

  T create(T t);
}
