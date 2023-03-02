package com.exercise.sfe.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionEntity extends Exception {

  public int code;
  public String message;
}
