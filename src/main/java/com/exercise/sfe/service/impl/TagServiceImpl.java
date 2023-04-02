package com.exercise.sfe.service.impl;

import com.exercise.sfe.constant.ErrorCodes;
import com.exercise.sfe.constant.ErrorMessages;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.TagDto;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.mapper.TagMapper;
import com.exercise.sfe.repository.TagRepository;
import com.exercise.sfe.service.TagService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

  public TagRepository tagRepository;
  public TagMapper tagMapper;

  @Override
  public List<Tag> getAll() {
    return tagRepository.getAll();
  }

  @Override
  public Tag getById(Long id) {
    return tagRepository.getById(id).stream().findFirst().orElseThrow(
        () -> new PersistenceException(
            ErrorCodes.TAG_NOT_FOUND,
            String.format(ErrorMessages.TAG_NOT_FOUND, id))
    );
  }

  @Override
  public Tag create(TagDto tagDto) {
    try {
      Tag tag = tagMapper.toTag(tagDto);
      return tagRepository.create(tag);
    } catch (Exception ex) {
      throw new PersistenceException(
          ErrorCodes.TAG_CREATION_IS_NOT_POSSIBLE,
          String.format(ErrorMessages.TAG_CREATION_IS_NOT_POSSIBLE, tagDto.getName()));
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
