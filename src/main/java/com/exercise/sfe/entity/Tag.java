package com.exercise.sfe.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Tag extends AbstractEntity {

  private String name;
}
