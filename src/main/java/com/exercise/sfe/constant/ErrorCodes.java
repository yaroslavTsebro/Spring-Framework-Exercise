package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorCodes {

  public final int TAG_NOT_FOUND = 40401;
  public final int TAG_NOT_FOUND_BY_NAME = 40401;
  public final int TAG_DELETION_IS_NOT_POSSIBLE = 40901;
  public final int TAG_CREATION_IS_NOT_POSSIBLE = 50301;

  public final int GIFT_CERTIFICATE_NOT_FOUND = 40411;
  public final int GIFT_CERTIFICATE_CREATION_IS_NOT_POSSIBLE = 50311;
  public final int GIFT_CERTIFICATE_DELETION_IS_NOT_POSSIBLE = 40911;
  public final int GIFT_CERTIFICATE_UPDATING_IS_NOT_POSSIBLE = 50312;

  public final int TAG_TO_GIFT_CERTIFICATE_NOT_FOUND = 40421;
  public final int TAG_TO_GIFT_CERTIFICATE_CREATING_IS_NOT_POSSIBLE = 50321;

  public final int SEARCH_FILTER_WRONG_DATA = 42299;
}