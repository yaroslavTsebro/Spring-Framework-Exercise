package com.exercise.sfe.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Tag extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;
  private String name;
}
