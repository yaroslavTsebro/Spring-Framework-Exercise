package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GiftCertificateSQLQueries {

  public final String GET_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate;";
  public final String GET_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?;";
  public final String SEARCH_GIFT_CERTIFICATE_BASE = "SELECT DISTINCT gc.* FROM gift_certificate gc "
      + "LEFT JOIN gift_certificate_to_tag gct on gc.id = gct.gift_certificate_id "
      + "LEFT JOIN tag t on t.id = gct.tag_id ";

  public final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate "
      + "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?);";
  public final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id=?;";
  public final String UPDATE_GIFT_CERTIFICATE_BASE = "UPDATE gift_certificate SET ";
}
