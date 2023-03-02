package com.exercise.sfe.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler
  ResponseEntity<ExceptionEntity> handler(PersistenceException ex) {
    ExceptionEntity errorBody = new ExceptionEntity(ex.getCode(), ex.getBody());
    return ResponseEntity.status(HttpStatus.valueOf(errorBody.getCode() / 100)).body(errorBody);
  }
}
