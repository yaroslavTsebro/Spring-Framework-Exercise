package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {

  public final String TAG_NOT_FOUND = "Tag was not found (id=%s)";
  public final String TAG_NOT_FOUND_BY_NAME = "Tag was not found (name=%s)";
  public final String TAG_DELETION_IS_NOT_POSSIBLE = "Tag deletion is not possible (id=%s)";
  public final String TAG_CREATION_IS_NOT_POSSIBLE = "Tag creation is not possible (name=%s)";

  public final String GIFT_CERTIFICATE_NOT_FOUND = "Gift certificate was not found (id=%s)";
  public final String GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE = "Gift certificate updating is not possible (id=%s)";
  public final String GIFT_CERTIFICATE_CREATION_IS_NOT_POSSIBLE = "Gift certificate creation is not possible (name=%s)";
  public final String GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE = "Gift certificate deletion is not possible (name=%s)";

  public final String TAG_TO_GIFT_CERTIFICATE_NOT_FOUND = "Cant delete connection between tag and gift certificate (tag_id=%s)";
  public final String TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE = "Cant create connection between tag and gift certificate (tag_id=%s)";

  public final String SEARCH_FILTER_WRONG_DATA = "Wrong search filter";

}
