package com.exercise.sfe.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PersistenceException extends RuntimeException {

  private final int code;
  private final String body;
}
