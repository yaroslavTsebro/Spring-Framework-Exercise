package com.exercise.sfe.service.impl;

import com.exercise.sfe.constant.ErrorCodes;
import com.exercise.sfe.constant.ErrorMessages;
import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.exception.PersistenceException;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.repository.TagRepository;
import com.exercise.sfe.service.GiftCertificateService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

  private GiftCertificateRepository giftCertificateRepository;
  private TagRepository tagRepository;

  public List<GiftCertificate> getAll() {
    var list = giftCertificateRepository.getAll();
    for (GiftCertificate gc : list) {
      gc.setTags(tagRepository.findAllByGiftCertificateId(gc.getId()));
    }
    return list;
  }

  @Override
  public List<GiftCertificate> getAll(SearchingSettings settings) {
    List<GiftCertificate> giftCertificateList = null;
    try {
      giftCertificateList = giftCertificateRepository.findAll(settings);
      if (giftCertificateList != null && !giftCertificateList.isEmpty()) {
        for (GiftCertificate gc : giftCertificateList) {
          gc.setTags(tagRepository.findAllByGiftCertificateId(gc.getId()));
        }
      }
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.SEARCH_FILTER_WRONG_DATA,
          ErrorMessages.SEARCH_FILTER_WRONG_DATA);
    }
    return giftCertificateList;
  }

  @Override
  public GiftCertificate getById(Long id) {
    var gc = giftCertificateRepository.getById(id);
    if (gc.isEmpty()) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_NOT_FOUND,
          String.format(ErrorMessages.GIFT_CERTIFICATE_NOT_FOUND, id));
    }
    gc.get().setTags(tagRepository.findAllByGiftCertificateId(id));
    return gc.get();
  }

  @Override
  public GiftCertificate update(GiftCertificate wantedGC) {
    var previousGC = giftCertificateRepository.getById(wantedGC.getId());
    if (previousGC.isEmpty()) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_NOT_FOUND,
          String.format(
              ErrorMessages.GIFT_CERTIFICATE_NOT_FOUND,
              wantedGC.getId())
      );
    }
    try {
      giftCertificateRepository.update(previousGC.get(), wantedGC);
      List<Tag> previousTags = tagRepository.findAllByGiftCertificateId(wantedGC.getId());
      updateTagsConnectedToGiftCertificate(previousTags, wantedGC.getTags(), wantedGC.getId());
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE,
          String.format(
              ErrorMessages.GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE,
              wantedGC.getId())
      );
    }
    return this.getById(wantedGC.getId());
  }

  public void updateTagsConnectedToGiftCertificate(List<Tag> previousTags, List<Tag> wantedTags,
      Long certificateId) {
    if (previousTags.equals(wantedTags)) {
      return;
    }
    if (wantedTags == null || wantedTags.isEmpty()) {
      detachTagsFromGiftCertificate(previousTags, certificateId);
      return;
    }
    if (previousTags.isEmpty()) {
      attachTagsToGiftCertificate(wantedTags, certificateId);
      return;
    }
    List<Tag> needToConnect = wantedTags;
    needToConnect.removeAll(previousTags);

    List<Tag> needToRemoveConnection = previousTags;
    needToRemoveConnection.removeAll(wantedTags);

    attachTagsToGiftCertificate(needToConnect, certificateId);
    detachTagsFromGiftCertificate(needToRemoveConnection, certificateId);
  }

  public void detachTagsFromGiftCertificate(List<Tag> tags, Long certificateId) {
    for (Tag tag : tags) {
      int result = tagRepository.deleteConnectionToGiftCertificate(tag.getId(), certificateId);
      if (result == 0) {
        throw new PersistenceException(
            ErrorCodes.TAG_TO_GIFT_CERTIFICATE_NOT_FOUND,
            String.format(
                ErrorMessages.TAG_TO_GIFT_CERTIFICATE_NOT_FOUND,
                tag.getId())
        );
      }
    }
  }

  public void attachTagsToGiftCertificate(List<Tag> tags, Long certificateId) {
    for (Tag tag : tags) {
      try {
        tagRepository.connectTagToGiftCertificate(tag.getId(), certificateId);
      } catch (Exception e) {
        throw new PersistenceException(
            ErrorCodes.TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE,
            String.format(
                ErrorMessages.TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE,
                tag.getId())
        );
      }
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      List<Tag> tags = tagRepository.findAllByGiftCertificateId(id);
      detachTagsFromGiftCertificate(tags, id);
      giftCertificateRepository.deleteById(id);
    } catch (Exception e) {
      throw new PersistenceException(
          ErrorCodes.GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE,
          String.format(ErrorMessages.GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE, id));
    }
  }

  @Override
  public GiftCertificate create(GiftCertificate giftCertificate) {
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
      if (t.getId() == null) {
        var createdTag = tagRepository.create(t);
        tagRepository.connectTagToGiftCertificate(createdTag.getId(), certificateId);
      } else {
        var tag = tagRepository.getById(t.getId());
        if (tag.isPresent()) {
          tagRepository.connectTagToGiftCertificate(t.getId(), certificateId);
        } else {
          throw new PersistenceException(
              ErrorCodes.TAG_NOT_FOUND,
              String.format(ErrorMessages.TAG_NOT_FOUND, t.getId())
          );
        }
      }
    }
  }
}
