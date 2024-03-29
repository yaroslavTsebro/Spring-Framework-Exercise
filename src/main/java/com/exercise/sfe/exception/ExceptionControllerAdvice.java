package com.exercise.sfe.exception;


import com.exercise.sfe.entity.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ApiErrorResponseDto> handler(PersistenceException ex) {
    ApiErrorResponseDto errorResponse = new ApiErrorResponseDto(ex.getCode(), ex.getBody());
    return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode() / 100)).body(errorResponse);
  }
}
