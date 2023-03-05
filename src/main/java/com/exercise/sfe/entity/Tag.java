package com.exercise.sfe.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Tag extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String name;
}
