package com.exercise.sfe.service.impl;

import com.exercise.sfe.constant.ErrorCodes;
import com.exercise.sfe.constant.ErrorMessages;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.repository.TagRepository;
import com.exercise.sfe.service.TagService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

  public TagRepository tagRepository;

  @Override
  public List<Tag> getAll() {
    return tagRepository.getAll();
  }

  @Override
  public Tag getById(Long id) {
    var tag = tagRepository.getById(id);
    if (tag.isEmpty()) {
      throw new PersistenceException(
          ErrorCodes.TAG_NOT_FOUND,
          String.format(ErrorMessages.TAG_NOT_FOUND, id));
    }
    return tag.get();
  }

  @Override
  public Tag create(Tag tag) {
    try {
      return tagRepository.create(tag);
    } catch (Exception ex) {
      throw new PersistenceException(
          ErrorCodes.TAG_CREATION_IS_NOT_POSSIBLE,
          String.format(ErrorMessages.TAG_CREATION_IS_NOT_POSSIBLE, tag.getName()));
    }

  }

  @Override
  public void deleteById(Long id) {
    int result = tagRepository.deleteById(id);
    if (result == 0) {
      throw new PersistenceException(
          ErrorCodes.TAG_DELETION_IS_NOT_POSSIBLE,
          String.format(ErrorMessages.TAG_DELETION_IS_NOT_POSSIBLE, id));
    }
  }
}
