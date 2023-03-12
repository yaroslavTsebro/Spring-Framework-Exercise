package com.exercise.sfe.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.exercise.sfe.EntityFactory;
import com.exercise.sfe.configuration.TestConfiguration;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.repository.TagRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TagRepositoryImplTest {

  @Autowired
  private TagRepository tagRepository;

  @Test
  void getAll_shouldReturnAllTags() {
    List<Tag> expected = EntityFactory.createTag(new Long[]{1L, 2L, 3L});
    List<Tag> actual = tagRepository.getAll();
    assertEquals(expected, actual);
  }

  @Test
  void getById_shouldReturnTagById() {
    Long id = 1L;
    Optional<Tag> expected = Optional.of(EntityFactory.createTag(id));
    Optional<Tag> actual = tagRepository.getById(id);
    assertTrue(actual.isPresent());
    assertEquals(expected, actual);
  }

  @Test
  void create_shouldCreateTag() {
    Long id = 5L;
    Tag expected = EntityFactory.createTag(id);
    Tag actual = tagRepository.create(expected);
    assertEquals(expected, actual);
  }

  @Test
  void deleteById_shouldDeleteTagById() {
    long createTagId = tagRepository.create(EntityFactory.createTag(6L)).getId();
    tagRepository.deleteById(createTagId);
    assertTrue(tagRepository.getById(createTagId).isEmpty());
  }

  @Test
  void getByName_shouldReturnByName() {
    Optional<Tag> expected = Optional.of(EntityFactory.createTag(1L));
    String name = expected.get().getName();
    Optional<Tag> actual = tagRepository.getByName(name);
    assertTrue(actual.isPresent());
    assertEquals(expected, actual);
  }

  @Test
  void getAllByGiftCertificateId_shouldReturnAllTagsThatConnectedToGiftCertificateWithId() {
    Long id = 1L;
    List<Tag> expected = EntityFactory.createTag(new Long[]{1L, 2L, 3L});
    List<Tag> actual = tagRepository.getAllByGiftCertificateId(1L);
    assertEquals(expected, actual);
  }

  @Test
  void connectTagToGiftCertificate_shouldCreateConnectionBetweenTagAndGiftCertificateByIds() {
    Long certificateId = 1L;
    List<Tag> expected = EntityFactory.createTag(new Long[]{1L, 2L, 3L});
    Tag tag = EntityFactory.createTag();
    tagRepository.create(tag);
    expected.add(tag);
    tagRepository.connectTagToGiftCertificate(tag.getId(), certificateId);

    assertEquals(expected, tagRepository.getAllByGiftCertificateId(certificateId));
  }

  @Test
  void deleteConnectionToGiftCertificate_shouldDeleteConnectionBetweenTagAndGiftCertificateByIds() {
    Long certificateId = 1L;
    List<Tag> expectedTags = tagRepository.getAllByGiftCertificateId(certificateId);
    Tag expected = EntityFactory.createTag();
    Tag tag = tagRepository.create(expected);
    tagRepository.connectTagToGiftCertificate(tag.getId(), certificateId);

    tagRepository.deleteConnectionToGiftCertificate(tag.getId(), certificateId);

    var actualTags = tagRepository.getAllByGiftCertificateId(certificateId);
    assertEquals(expectedTags, actualTags);
  }
}