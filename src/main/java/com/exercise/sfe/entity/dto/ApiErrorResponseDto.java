package com.exercise.sfe.entity.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiErrorResponseDto {

  public final int errorCode;
  public final String errorMessage;
}
