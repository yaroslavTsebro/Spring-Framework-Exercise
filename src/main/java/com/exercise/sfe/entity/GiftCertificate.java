package com.exercise.sfe.entity;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GiftCertificate extends AbstractEntityWithMetadata {

  private static final long serialVersionUID = 7623089836024818946L;
  private String name;
  private String description;
  private BigDecimal price;
  private int duration;
  private List<Tag> tags;
}
