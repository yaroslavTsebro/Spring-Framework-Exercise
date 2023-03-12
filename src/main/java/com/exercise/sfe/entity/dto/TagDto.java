package com.exercise.sfe.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TagDto {

  @NotNull
  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String name;
}
