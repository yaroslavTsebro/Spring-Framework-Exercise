package com.exercise.sfe.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionEntity {

  public int errorCode;
  public String errorMessage;
}
