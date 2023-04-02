package com.exercise.sfe.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public abstract class AbstractEntity implements Serializable {

  private Long id;
}
