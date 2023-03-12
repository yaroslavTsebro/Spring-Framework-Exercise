package com.exercise.sfe.repository.querybuilder;

/**
 * The interface describes behavior for creating SQL queries that updates optionally
 */
public interface UpdateQueryBuilder<T> {

  /**
   *  Builds query that optionally updates entity
   *
   * @param baseQuery The skeleton query
   * @param wanted The entity to update
   * @return query with optionally affected DML params
   */
  String buildUpdateQuery(String baseQuery, T wanted);
}
