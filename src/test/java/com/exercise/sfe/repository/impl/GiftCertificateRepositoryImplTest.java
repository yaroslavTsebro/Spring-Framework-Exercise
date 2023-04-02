package com.exercise.sfe.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.exercise.sfe.EntityFactory;
import com.exercise.sfe.configuration.TestConfiguration;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.repository.TagRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
@WebAppConfiguration
class GiftCertificateRepositoryImplTest {

  @Autowired
  private GiftCertificateRepository giftCertificateRepository;
  @Autowired
  private TagRepository tagRepository;

  @Test
  void getAll_shouldReturnAllGiftCertificates() {
    var expected = EntityFactory.createListOfGiftCertificates(new Long[]{1L, 2L});
    var actual = giftCertificateRepository.getAll();

    assertEquals(expected, actual);
  }

  @Test
  void getById_shouldReturnGiftCertificateById() {
    Long id = 1L;
    Optional<GiftCertificate> expected = Optional.of(EntityFactory.createGiftCertificate(id));
    var actual = giftCertificateRepository.getById(id);

    assertEquals(expected, actual);
  }

  @Test
  void deleteById_shouldDeleteGiftCertificateById() {
    Long createId = 6L;
    Long giftCertificateId = giftCertificateRepository.create(
        EntityFactory.createGiftCertificate(createId)
    ).getId();
    giftCertificateRepository.deleteById(giftCertificateId);
    assertTrue(giftCertificateRepository.getById(giftCertificateId).isEmpty());
  }

  @Test
  void create_shouldCreateAndReturnGiftCertificate() {
    Long id = 5L;
    var expected = EntityFactory.createGiftCertificate(id);
    var actual = giftCertificateRepository.create(expected);
    assertEquals(expected, actual);
  }

  @Test
  void update_shouldUpdateGiftCertificate() {
    Long id = 1L;
    String testName = "testName";
    BigDecimal testPrice = new BigDecimal("11111.00");

    var expected = giftCertificateRepository.getById(id).get();
    expected.setName(testName);
    expected.setPrice(testPrice);
    var prevTags = tagRepository.getAllByGiftCertificateId(expected.getId());
    expected.setTags(prevTags);

    var entityToUpdate = new GiftCertificate();
    entityToUpdate.setId(id);
    entityToUpdate.setName(testName);
    entityToUpdate.setPrice(testPrice);
    giftCertificateRepository.update(entityToUpdate);

    var actual = giftCertificateRepository.getById(id).get();
    actual.setTags(tagRepository.getAllByGiftCertificateId(id));

    expected.setLastUpdateDate(actual.getLastUpdateDate());
    assertEquals(expected, actual);
  }

  @Test
  void getAll_shouldReturnAllGiftCertificatesBySettings() {
    String testName = "testName";
    createAllForGetAllBySettings();
    var settings = EntityFactory.createSearchingSettings(testName, null, "desc", null);
    var expected = giftCertificateRepository.getAll();
    expected.forEach(s -> s.setTags(tagRepository.getAllByGiftCertificateId(s.getId())));
    expected = filterListAccordingToSettings(expected, settings);

    var actual = giftCertificateRepository.getAll(settings);
    actual.forEach(s -> s.setTags(tagRepository.getAllByGiftCertificateId(s.getId())));

    assertEquals(expected, actual);
  }

  private void createAllForGetAllBySettings() {
    String testName = "testName";

    var certificatesToCreate = EntityFactory.createListOfGiftCertificates(new Long[]{4L, 5L});
    var tags = EntityFactory.createListOfTags(new Long[]{4L, 5L});
    certificatesToCreate.get(0).setTags(tags);
    var newTag = new Tag();
    newTag.setName(testName);
    tags.add(newTag);
    certificatesToCreate.get(1).setTags(tags);

    certificatesToCreate.forEach(s -> giftCertificateRepository.create(s));
    tags.forEach(s -> tagRepository.create(s));
    tags.forEach(s -> tagRepository.connectTagToGiftCertificate(s.getId(), 4L));
  }


  private List<GiftCertificate> filterListAccordingToSettings(List<GiftCertificate> certificates,
      SearchingSettings settings) {
    List<GiftCertificate> resultList = certificates;

    if (settings.getTagName() != null) {
      String tagName = settings.getTagName();
      resultList = resultList.stream()
          .filter(c -> Objects.nonNull(c.getTags()))
          .filter(c -> c.getTags().stream()
              .anyMatch(tag -> tag.getName().contains(tagName))
          )
          .collect(Collectors.toList());
    }
    if (settings.getGiftCertificateName() != null) {
      String name = settings.getGiftCertificateName();
      resultList = resultList.stream()
          .filter(c -> c.getName().contains(name))
          .collect(Collectors.toList());
    }
    if (settings.getGiftCertificateDescription() != null) {
      String description = settings.getGiftCertificateDescription();
      resultList = resultList.stream()
          .filter(c -> c.getDescription().contains(description))
          .collect(Collectors.toList());
    }
    return resultList;
  }
}