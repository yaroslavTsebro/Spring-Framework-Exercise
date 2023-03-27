package com.exercise.sfe.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Tag extends AbstractEntity {

  private static final long serialVersionUID = -4386959427886555570L;
  private String name;
}
