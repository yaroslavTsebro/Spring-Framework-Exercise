package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagSQLQueries {

  public final String GET_ALL_TAGS = "SELECT * FROM tag;";
  public final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id=?;";
  public final String GET_TAG_BY_NAME_LIKE = "SELECT * FROM tag WHERE name LIKE ?;";
  public final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?);";
  public final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE tag.id=?;";
}
