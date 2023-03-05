package com.exercise.sfe.exception;


import com.exercise.sfe.entity.exception.ExceptionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler
  ResponseEntity<ExceptionEntity> handler(PersistenceException ex) {
    ExceptionEntity errorBody = new ExceptionEntity(ex.getCode(), ex.getBody());
    return ResponseEntity.status(HttpStatus.valueOf(errorBody.getErrorCode() / 100)).body(errorBody);
  }
}
