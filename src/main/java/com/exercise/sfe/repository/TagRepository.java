package com.exercise.sfe.repository;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import java.util.List;
import java.util.Optional;

/**
 * This interface describes abstract behavior and data access functionality for the {@link Tag} entity
 */
public interface TagRepository extends CrdRepository<Tag> {

  /**
   * Finds {@link Tag} by given name
   *
   * @param name the name of {@link Tag}
   * @return {@link Optional} of {@link Tag} entity
   */
  Optional<Tag> getByName(String name);

  /**
   * Finds entities by {@link GiftCertificate} id
   *
   * @param id The id of {@link GiftCertificate}
   * @return {@link List} of {@link Tag} entities
   */
  List<Tag> getAllByGiftCertificateId(Long id);

  /**
   * Creates connection between {@link Tag} and {@link GiftCertificate}
   *
   * @param tagId The id of {@link Tag}
   * @param gcId The id of {@link GiftCertificate}
   */
  void connectTagToGiftCertificate(Long tagId, Long gcId);

  /**
   * Delete connection between {@link Tag} and {@link GiftCertificate}
   *
   * @param tagId The id of {@link Tag}
   * @param gcId The id of {@link GiftCertificate}
   * @return count of deleted connections
   */
  int deleteConnectionToGiftCertificate(Long tagId, Long gcId);

}
