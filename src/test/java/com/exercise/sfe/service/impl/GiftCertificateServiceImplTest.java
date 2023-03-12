package com.exercise.sfe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.exercise.sfe.EntityFactory;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.GiftCertificateDto;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.mapper.GiftCertificateMapperImpl;
import com.exercise.sfe.mapper.TagMapperImpl;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

  @Mock
  private TagRepository tagRepository;

  @Mock
  private GiftCertificateRepository giftCertificateRepository;

  @Spy
  private GiftCertificateMapperImpl giftCertificateMapper;

  @InjectMocks
  private GiftCertificateServiceImpl giftCertificateService;

  @Test
  void getAll_shouldReturnListOfCertificates() {
    List<GiftCertificate> giftCertificates = new ArrayList<>();
    giftCertificates.add(EntityFactory.createGiftCertificate(1L));
    given(giftCertificateRepository.getAll()).willReturn(giftCertificates);

    var actual = giftCertificateService.getAll();

    then(tagRepository).should().getAllByGiftCertificateId(anyLong());
    then(giftCertificateRepository).should().getAll();
    assertEquals(giftCertificates, actual);
  }

  @Test
  void getAllBySettings() {
    SearchingSettings settings = mock(SearchingSettings.class);
    var giftCertificate = EntityFactory.createGiftCertificate(new Long[]{1L});
    given(giftCertificateRepository.getAll(settings)).willReturn(giftCertificate);

    var actual = giftCertificateService.getAll(settings);

    then(tagRepository).should().getAllByGiftCertificateId(anyLong());
    then(giftCertificateRepository).should().getAll(settings);
    assertEquals(giftCertificate, actual);
  }

  @Test
  void getById_shouldReturnCertificate() {
    var giftCertificate = EntityFactory.createGiftCertificate(1L);
    given(giftCertificateRepository.getById(giftCertificate.getId())).willReturn(
        Optional.of(giftCertificate));

    GiftCertificate actual = giftCertificateService.getById(giftCertificate.getId());

    then(giftCertificateRepository).should().getById(anyLong());
    assertEquals(giftCertificate, actual);
  }

  @Test
  void getById_shouldThrowException_whenCertificateNotFound() {
    assertThrows(PersistenceException.class, () -> giftCertificateService.getById(1L));
  }

  @Test
  void update_shouldThrowPersistenceException_ifUpdatingIsNotPossible() {
    Long id = -1L;
    var certificate = EntityFactory.createGiftCertificate(id);
    GiftCertificateDto dto = giftCertificateMapper.toGiftCertificateDto(certificate);

    given(giftCertificateRepository.getById(anyLong())).willReturn(Optional.empty());
    assertThrows(PersistenceException.class, () -> giftCertificateService.update(dto, id));
  }

  @Test
  void delete_shouldDeleteCertificate() {
    var giftCertificate = EntityFactory.createGiftCertificate();
    long id = giftCertificate.getId();

    giftCertificateService.deleteById(id);

    then(giftCertificateRepository).should().deleteById(anyLong());
  }

  @Test
  void delete_shouldThrowPersistenceException_whenCertificateNotFound() {
    when(giftCertificateRepository.deleteById(anyLong())).thenThrow(RuntimeException.class);
    assertThrows(PersistenceException.class, () -> giftCertificateService.deleteById(-1L));
  }

  @Test
  void create_shouldThrowPersistenceException_ifCreationIsNotPossible() {
    var certificate = EntityFactory.createGiftCertificate(1L);
    GiftCertificateDto dto = giftCertificateMapper.toGiftCertificateDto(certificate);
    when(giftCertificateRepository.create(any(GiftCertificate.class))).thenThrow(
        PersistenceException.class);
    assertThrows(PersistenceException.class, () -> giftCertificateService.create(dto));
  }

  @Test
  void create_shouldCreateGiftCertificateAndReturnIt() {
    var certificate = EntityFactory.createGiftCertificate(5L);
    List<Tag> tags = EntityFactory.createTag(new Long[]{1L, 2L, 4L});
    certificate.setTags(tags);

    given(giftCertificateRepository.create(any())).willReturn(certificate);
    given(tagRepository.create(any())).willReturn(tags.get(2));
    given(tagRepository.getByName(tags.get(0).getName())).willReturn(Optional.of(tags.get(0)));
    given(tagRepository.getByName(tags.get(1).getName())).willReturn(Optional.of(tags.get(1)));
    given(tagRepository.getByName(tags.get(1).getName())).willReturn(Optional.of(tags.get(2)));


    GiftCertificateDto dto = giftCertificateMapper.toGiftCertificateDto(certificate);

    giftCertificateService.create(dto);

    then(giftCertificateRepository).should().create(any());
    verify(tagRepository, times(3)).connectTagToGiftCertificate(anyLong(), anyLong());
  }
}