package com.exercise.sfe.repository.querybuilder.impl;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.repository.querybuilder.UpdateQueryBuilder;
import java.util.HashMap;
import java.util.Map;


public class GiftCertificateUpdateQueryBuilder implements UpdateQueryBuilder<GiftCertificate> {

  @Override
  public String buildUpdateQuery(String baseQuery, GiftCertificate wanted) {
    Map<String, String> wantedParams = getFieldMapFromInstance(wanted);

    return buildQuery(baseQuery, wantedParams, wanted.getId());
  }

  private Map<String, String> getFieldMapFromInstance(GiftCertificate giftCertificate) {
    Map<String, String> fieldsAndValues = new HashMap<>();
    if (giftCertificate.getName() != null) {
      fieldsAndValues.put("name", giftCertificate.getName());
    }
    if (giftCertificate.getDescription() != null) {
      fieldsAndValues.put("description", giftCertificate.getDescription());
    }
    if (giftCertificate.getPrice() != null) {
      fieldsAndValues.put("price", giftCertificate.getPrice().toString());
    }
    if (giftCertificate.getDuration() > 0) {
      fieldsAndValues.put("duration", String.valueOf(giftCertificate.getDuration()));
    }
    return fieldsAndValues;
  }


  private String buildQuery(String base, Map<String, String> params, Long id) {
    StringBuilder pairsPart = new StringBuilder();

    boolean isFirstInserted = false;
    for (var pair : params.entrySet()) {
      if (!isFirstInserted) {
        isFirstInserted = true;
      } else {
        pairsPart.append(", ");
      }
      pairsPart.append(pair.getKey());
      pairsPart.append(" = '");
      pairsPart.append(pair.getValue());
      pairsPart.append("' ");
    }
    base += " last_update_date" + " =?, ";
    base = base + pairsPart + "WHERE id = " + id + ";";
    return base;
  }
}
