package com.exercise.sfe;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.entity.search.SortingTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityFactory {

  private final LocalDateTime time = LocalDateTime.parse("2023-03-08T12:28:17");
  private static AtomicLong counter = new AtomicLong(4L);

  public Tag createTag(Long id) {
    var tag = new Tag();
    tag.setId(id);
    tag.setName("tag" + id);
    return tag;
  }

  public List<Tag> createTag(Long[] ids) {
    return Arrays.stream(ids)
        .map(EntityFactory::createTag)
        .collect(Collectors.toList());
  }

  public Tag createTag() {
    Long num = counter.get();
    var tag = createTag(num);
    counter.getAndIncrement();
    return tag;
  }

  public GiftCertificate createGiftCertificate(Long id) {
    var giftCertificate = new GiftCertificate();
    giftCertificate.setId(id);
    giftCertificate.setName("certificate" + id);
    giftCertificate.setDescription("description" + id);
    String price = id.toString() + ".00";
    giftCertificate.setPrice(new BigDecimal(price));
    giftCertificate.setDuration(Math.toIntExact(id));
    giftCertificate.setCreateDate(time);
    giftCertificate.setLastUpdateDate(time);
    return giftCertificate;
  }

  public GiftCertificate createGiftCertificate() {
    Long num = counter.get();
    var tag = createGiftCertificate(num);
    counter.getAndIncrement();
    return tag;
  }

  public SearchingSettings createSearchingSettings(
      String tagName,
      String giftCertificateName,
      String giftCertificateDescription,
      Map<String, SortingTypes> sortingMap) {
    SearchingSettings settings = new SearchingSettings();
    settings.setTagName(tagName);
    settings.setGiftCertificateName(giftCertificateName);
    settings.setGiftCertificateDescription(giftCertificateDescription);
    settings.setSortingMap(sortingMap);
    return settings;
  }


  public List<GiftCertificate> createGiftCertificate(Long[] ids) {
    return Arrays.stream(ids)
        .map(EntityFactory::createGiftCertificate)
        .collect(Collectors.toList());
  }

}
