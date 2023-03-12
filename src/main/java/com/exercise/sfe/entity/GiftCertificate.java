package com.exercise.sfe.entity;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GiftCertificate extends AbstractEntityWithMetadata {

  private String name;

  private String description;

  private BigDecimal price;

  private int duration;

  private List<Tag> tags;
}
