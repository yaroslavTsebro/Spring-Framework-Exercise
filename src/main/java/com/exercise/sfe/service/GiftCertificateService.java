package com.exercise.sfe.service;


import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.dto.GiftCertificateDto;
import com.exercise.sfe.entity.search.SearchingSettings;
import java.util.List;

public interface GiftCertificateService extends CrudService<GiftCertificate, GiftCertificateDto> {

  /**
   * Finds all {@link GiftCertificate} entities by settings
   *
   * @param settings The {@link SearchingSettings} entity
   * @return {@link List} of {@link GiftCertificate}
   */
  List<GiftCertificate> getAll(SearchingSettings settings);

  /**
   * Finds all {@link GiftCertificate} according to presence of settings
   *
   * @param settings The {@link SearchingSettings} entity
   * @return {@link List} of {@link GiftCertificate}
   */
  List<GiftCertificate> getAllAccordingToSettings(SearchingSettings settings);

}
