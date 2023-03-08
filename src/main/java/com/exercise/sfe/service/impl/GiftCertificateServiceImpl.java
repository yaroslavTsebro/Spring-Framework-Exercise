package com.exercise.sfe.service.impl;

import com.exercise.sfe.constant.ErrorCodes;
import com.exercise.sfe.constant.ErrorMessages;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.GiftCertificateDto;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.mapper.GiftCertificateMapper;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.repository.TagRepository;
import com.exercise.sfe.service.GiftCertificateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

  private GiftCertificateRepository giftCertificateRepository;
  private TagRepository tagRepository;
  private GiftCertificateMapper giftCertificateMapper;

  public List<GiftCertificate> getAll() {
    return giftCertificateRepository.getAll()
        .stream()
        .peek(s -> {
          try {
            s.setTags(tagRepository.getAllByGiftCertificateId(s.getId()));
          } catch (Exception e) {
            throw new PersistenceException(
                ErrorCodes.TAG_NOT_FOUND,
                String.format(ErrorMessages.TAG_NOT_FOUND, s.getId()));
          }
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<GiftCertificate> getAllAccordingToSettings(SearchingSettings settings) {
    if (settings == null) {
      return getAll();
    } else {
      return getAll(settings);
    }
  }


  @Override
  public List<GiftCertificate> getAll(SearchingSettings settings) {

    return giftCertificateRepository.getAll(settings)
        .stream()
        .peek(s -> {
          try {
            s.setTags(tagRepository.getAllByGiftCertificateId(s.getId()));
          } catch (Exception e) {
            throw new PersistenceException(
                ErrorCodes.SEARCH_FILTER_WRONG_DATA,
                String.format(ErrorMessages.SEARCH_FILTER_WRONG_DATA, s.getId()));
          }
        })
        .collect(Collectors.toList());
  }

  @Override
  public GiftCertificate getById(Long id) {
    return giftCertificateRepository.getById(id)
        .map(this::enrichByTags)
        .orElseThrow(
            () -> new PersistenceException(
                ErrorCodes.GIFT_CERTIFICATE_NOT_FOUND,
                String.format(ErrorMessages.GIFT_CERTIFICATE_NOT_FOUND, id))
        );
  }

  private GiftCertificate enrichByTags(GiftCertificate giftCertificate) {
    giftCertificate.setTags(tagRepository.getAllByGiftCertificateId(giftCertificate.getId()));
    return giftCertificate;
  }

  @Transactional
  @Override
  public GiftCertificate update(GiftCertificateDto giftCertificateDto, Long id) {
    GiftCertificate wantedGiftCertificate = giftCertificateMapper
        .toGiftCertificate(giftCertificateDto);
    giftCertificateRepository.getById(id).orElseThrow(
        () -> {
          throw new PersistenceException(
              ErrorCodes.GIFT_CERTIFICATE_NOT_FOUND,
              String.format(
                  ErrorMessages.GIFT_CERTIFICATE_NOT_FOUND,
                  wantedGiftCertificate.getId())
          );
        });
    try {
      wantedGiftCertificate.setId(id);
      giftCertificateRepository.update(wantedGiftCertificate);
      if (wantedGiftCertificate.getTags() != null) {
        List<Tag> previousTags = tagRepository.getAllByGiftCertificateId(id);
        updateTagsConnectedToGiftCertificate(previousTags, wantedGiftCertificate.getTags(), id);
      }
    } catch (PersistenceException e) {
      throw new PersistenceException(e.getCode(), e.getBody());
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE,
          String.format(
              ErrorMessages.GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE, id)
      );
    }
    return this.getById(id);
  }

  public void updateTagsConnectedToGiftCertificate(List<Tag> previousTags, List<Tag> wantedTags,
      Long certificateId) {
    if (wantedTags.isEmpty()) {
      detachTagsFromGiftCertificate(previousTags, certificateId);
      return;
    }
    if (previousTags.isEmpty()) {
      attachTagsToGiftCertificate(wantedTags, certificateId);
      return;
    }
    List<Tag> needToConnect = new ArrayList<>(wantedTags);
    needToConnect.removeAll(previousTags);

    List<Tag> needToRemoveConnection = new ArrayList<>(previousTags);
    needToRemoveConnection.removeAll(wantedTags);

    attachTagsToGiftCertificate(needToConnect, certificateId);
    detachTagsFromGiftCertificate(needToRemoveConnection, certificateId);
  }

  public void detachTagsFromGiftCertificate(List<Tag> tags, Long certificateId) {
    for (Tag tag : tags) {
      var responseTag = tagRepository.getByName(tag.getName());
      if (responseTag.isEmpty()) {
        throw new PersistenceException(
            ErrorCodes.TAG_NOT_FOUND_BY_NAME,
            String.format(ErrorMessages.TAG_NOT_FOUND_BY_NAME, tag.getName())
        );
      }
      Long tagId = responseTag.get().getId();
      int result = tagRepository.deleteConnectionToGiftCertificate(tagId, certificateId);
      if (result == 0) {
        throw new PersistenceException(
            ErrorCodes.TAG_TO_GIFT_CERTIFICATE_NOT_FOUND,
            String.format(
                ErrorMessages.TAG_TO_GIFT_CERTIFICATE_NOT_FOUND, tagId)
        );
      }
    }
  }

  public void attachTagsToGiftCertificate(List<Tag> tags, Long certificateId) {
    for (Tag tag : tags) {
      var responseTag = tagRepository.getByName(tag.getName());
      if (responseTag.isEmpty()) {
        throw new PersistenceException(
            ErrorCodes.TAG_NOT_FOUND_BY_NAME,
            String.format(ErrorMessages.TAG_NOT_FOUND_BY_NAME, tag.getName())
        );
      }
      Long tagId = responseTag.get().getId();
      try {
        tagRepository.connectTagToGiftCertificate(tagId, certificateId);
      } catch (Exception e) {
        throw new PersistenceException(
            ErrorCodes.TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE,
            String.format(
                ErrorMessages.TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE, tagId)
        );
      }
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      List<Tag> tags = tagRepository.getAllByGiftCertificateId(id);
      detachTagsFromGiftCertificate(tags, id);
      giftCertificateRepository.deleteById(id);
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE,
          String.format(ErrorMessages.GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE, id));
    }
  }

  @Transactional
  @Override
  public GiftCertificate create(GiftCertificateDto giftCertificateDto) {
    GiftCertificate giftCertificate = giftCertificateMapper.toGiftCertificate(giftCertificateDto);
    try {
      var created = giftCertificateRepository.create(giftCertificate);
      if (giftCertificate.getTags() != null) {
        connectOrCreateAndConnectTags(giftCertificate.getTags(), giftCertificate.getId());
      }

      return created;
    } catch (PersistenceException pe) {
      throw new PersistenceException(pe.getCode(), pe.getBody());
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_CREATION_IS_NOT_POSSIBLE,
          String.format(
              ErrorMessages.GIFT_CERTIFICATE_CREATION_IS_NOT_POSSIBLE,
              giftCertificate.getName())
      );
    }
  }

  public void connectOrCreateAndConnectTags(List<Tag> tags, Long certificateId) {
    for (Tag t : tags) {
      Optional<Tag> foundTag = tagRepository.getByName(t.getName());
      if (foundTag.isEmpty()) {
        var createdTag = tagRepository.create(t);
        t.setId(createdTag.getId());
        tagRepository.connectTagToGiftCertificate(createdTag.getId(), certificateId);
      } else {
        t.setId(foundTag.get().getId());
        tagRepository.connectTagToGiftCertificate(foundTag.get().getId(), certificateId);
      }
    }
  }
}
