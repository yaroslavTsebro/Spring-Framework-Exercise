package com.exercise.sfe.utility.impl;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.utility.UpdateQueryBuilder;
import java.util.HashMap;
import java.util.Map;


public class GiftCertificateUpdateQueryBuilder implements UpdateQueryBuilder<GiftCertificate> {

  @Override
  public String buildUpdateQuery(String baseQuery, GiftCertificate prev,
      GiftCertificate wanted) {
    String resultQuery = baseQuery;
    Map<String, String> prevParams = getFieldMapFromInstance(prev);
    Map<String, String> wantedParams = getFieldMapFromInstance(wanted);

    Map<String, String> queryParams = getQueryParams(prevParams, wantedParams);

    return buildQuery(resultQuery, queryParams, wanted.getId());
  }

  private Map<String, String> getFieldMapFromInstance(GiftCertificate gc) {
    Map<String, String> fieldsAndValues = new HashMap<>();
    if (gc.getName() != null) {
      fieldsAndValues.put("name", gc.getName());
    }
    if (gc.getDescription() != null) {
      fieldsAndValues.put("description", gc.getDescription());
    }
    if (gc.getPrice() != null) {
      fieldsAndValues.put("price", gc.getPrice().toString());
    }
    if (gc.getDuration() > 0) {
      fieldsAndValues.put("duration", String.valueOf(gc.getDuration()));
    }
    return fieldsAndValues;
  }

  public Map<String, String> getQueryParams(Map<String, String> prev,
      Map<String, String> wanted) {
    Map<String, String> queryParams = new HashMap<>();
    for (var ps : prev.entrySet()) {
      for (var ws : wanted.entrySet()) {
        String pkey = ps.getKey();
        String wkey = ws.getKey();
        String pval = ps.getValue();
        String wval = ws.getValue();
        if (pkey.equals(wkey) && !pval.equals(wval)) {
          queryParams.put(pkey, wval);
        }
      }
    }
    return queryParams;
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

    base = base + pairsPart + "WHERE id = " + id + ";";
    return base;
  }
}
