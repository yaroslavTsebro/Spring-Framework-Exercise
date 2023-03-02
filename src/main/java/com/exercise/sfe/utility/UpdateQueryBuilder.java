package com.exercise.sfe.utility;


public interface UpdateQueryBuilder<T> {

  String buildUpdateQuery(String baseQuery, T prev, T wanted);
}
