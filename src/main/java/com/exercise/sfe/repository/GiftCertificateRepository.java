package com.exercise.sfe.repository;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.search.SearchingSettings;
import java.util.List;

/**
 * This interface describes abstract behavior and data access functionality for the {@link GiftCertificate} entity
 */
public interface GiftCertificateRepository extends CRUDRepository<GiftCertificate> {

  /**
   * Finds all {@link GiftCertificate} entities with specified parameters.
   *
   * @param settings object for search parameters
   * @return {@link List} of found {@link GiftCertificate}.
   */
  List<GiftCertificate> findAll(SearchingSettings settings);
}
