package com.exercise.sfe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.exercise.sfe.EntityFactory;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.TagDto;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.mapper.TagMapperImpl;
import com.exercise.sfe.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock
  private TagRepository tagRepository;

  @Spy
  private TagMapperImpl tagMapper;

  @InjectMocks
  private TagServiceImpl tagService;

  @Test
  void getAll_shouldReturnListOfTags() {
    List<Tag> expected = EntityFactory.createListOfTags(new Long[]{1L, 2L, 3L});
    given(tagRepository.getAll()).willReturn(expected);

    var actual = tagService.getAll();
    then(tagRepository).should().getAll();

    assertEquals(expected, actual);
  }

  @Test
  void getById_shouldReturnTag() {
    Long id = 1L;
    Tag expected = EntityFactory.createTag(id);
    given(tagRepository.getById(id)).willReturn(Optional.of(expected));

    Tag actual = tagService.getById(id);
    then(tagRepository).should().getById(id);

    assertEquals(expected, actual);
  }

  @Test
  void getById_shouldThrowPersistenceException_ifTagNotFound() {
    Long id = 1L;
    given(tagRepository.getById(anyLong())).willReturn(Optional.empty());

    assertThrows(PersistenceException.class, () -> tagService.getById(id));
  }

  @Test
  void create_shouldCreateTag() {
    var expected = EntityFactory.createTag(4L);
    TagDto dto = tagMapper.toTagDto(expected);
    given(tagRepository.create(any(Tag.class))).willReturn(expected);

    Tag actual = tagService.create(dto);

    then(tagRepository).should().create(any());
    assertEquals(expected, actual);
  }

  @Test
  void create_shouldThrowPersistenceException_ifCreationIsNotPossible() {
    var expected = EntityFactory.createTag(4L);
    TagDto dto = tagMapper.toTagDto(expected);
    given(tagRepository.create(any(Tag.class))).willThrow(
        PersistenceException.class);

    assertThrows(PersistenceException.class, () -> tagService.create(dto));
  }

  @Test
  void deleteById_shouldDeleteTag() {
    Long id = 1L;
    var tag = EntityFactory.createTag(id);
    given(tagRepository.deleteById(anyLong())).willReturn(1);

    tagService.deleteById(id);

    then(tagRepository).should().deleteById(id);
  }

  @Test
  void deleteById_shouldThrowPersistenceException_ifDeletionIsNotPossible() {
    assertThrows(PersistenceException.class, () -> tagService.deleteById(anyLong()));
  }
}