package com.exercise.sfe.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {

  public final String TAG_NOT_FOUND = "Tag was not found (id=%s)";
  public final String TAG_DELETION_IS_NOT_POSSIBLE = "Tag deletion is not possible (id=%s)";
  public final String TAG_CREATION_IS_NOT_POSSIBLE = "Tag creation is not possible (name=%s)";
}
