package com.exercise.sfe.repository.querybuilder.impl;

import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.entity.search.SortingTypes;
import com.exercise.sfe.repository.querybuilder.SearchQueryBuilder;
import io.micrometer.common.util.StringUtils;
import java.util.Map.Entry;


public class GiftCertificateSearchQueryBuilder implements SearchQueryBuilder {

  @Override
  public String buildSearchQueryBySettings(String baseQuery, SearchingSettings settings) {
    StringBuilder resultQuery = new StringBuilder(baseQuery);
    addLikeSetting("t.name", settings.getTagName(), resultQuery);
    addLikeSetting("gc.name", settings.getGiftCertificateName(), resultQuery);
    addLikeSetting("gc.description", settings.getGiftCertificateDescription(), resultQuery);
    if (settings.getSortingMap() != null && !settings.getSortingMap().isEmpty()) {
      settings.getSortingMap().entrySet().forEach(s -> this.addSortingFromMapEntry(s, resultQuery));
    }
    resultQuery.append(";");
    return resultQuery.toString();
  }

  private void addLikeSetting(String column, String searchLike, StringBuilder builder) {
    if (StringUtils.isEmpty(searchLike)) {
      return;
    }
    searchLike = "%" + searchLike + "%";
    if (builder.toString().contains("WHERE")) {
      builder.append(" AND ");
    } else {
      builder.append(" WHERE ");
    }
    builder.append(column + " LIKE " + "'" + searchLike + "'");
  }

  private void addSortingFromMapEntry(Entry<String, SortingTypes> pair, StringBuilder builder) {
    String key = pair.getKey();
    SortingTypes sortingType = pair.getValue();

    if (builder.toString().contains("ORDER BY")) {
      builder.append(", ");
    } else {
      builder.append(" ORDER BY ");
    }
    builder.append(key + " " + sortingType);
  }
}
