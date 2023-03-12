package com.exercise.sfe.repository.querybuilder;

import com.exercise.sfe.entity.search.SearchingSettings;

/**
 * The interface describes behavior for creating SQL queries that searches by settings.
 */
public interface SearchQueryBuilder {

  /**
   * Builds query that searches by settings with already affected settings.
   *
   * @param baseQuery The skeleton query
   * @param settings  The settings for query
   * @return The built query with all included params
   */
  String buildSearchQueryBySettings(String baseQuery, SearchingSettings settings);
}
