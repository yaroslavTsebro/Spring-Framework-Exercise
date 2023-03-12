package com.exercise.sfe.repository;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.search.SearchingSettings;
import java.util.List;


public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {

  /**
   * Finds all {@link GiftCertificate} entities with specified parameters.
   *
   * @param settings object for search parameters
   * @return {@link List} of found {@link GiftCertificate}.
   */
  List<GiftCertificate> getAll(SearchingSettings settings);
}
