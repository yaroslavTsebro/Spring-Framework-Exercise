package com.exercise.sfe.service;


import com.exercise.sfe.entity.Tag;
import java.util.List;

public interface TagService {

  /**
   * Finds all {@link Tag} entities
   *
   * @return {@link List} of found {@link Tag} entities
   */
  List<Tag> getAll();

  /**
   * Finds {@link Tag} by given name
   *
   * @param id an expected {@link Tag} id
   * @return Found {@link Tag}
   */
  Tag getById(Long id);

  Tag create(Tag tag);

  void deleteById(Long id);
}
