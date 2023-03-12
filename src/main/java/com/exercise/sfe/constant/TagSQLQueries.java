package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagSqlQueries {

  public final String GET_ALL_TAGS = "SELECT * FROM tag;";
  public final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id=?;";
  public final String GET_TAG_BY_NAME_LIKE = "SELECT * FROM tag WHERE name = ?;";
  public final String GET_ALL_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT t.* FROM tag t "
      + "LEFT JOIN gift_certificate_to_tag gc ON t.id = gc.tag_id WHERE gc.gift_certificate_id = ?";

  public final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?);";
  public final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE tag.id=?;";
  public final String CONNECT_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO gift_certificate_to_tag (gift_certificate_id, tag_id) VALUES (?, ?);";
  public final String DELETE_CONNECTION_TO_GIFT_CERTIFICATE = "DELETE FROM gift_certificate_to_tag g WHERE g.tag_id = ? AND g.gift_certificate_id = ?;";

}
