package com.exercise.sfe.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GiftCertificate extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 3L;
  private String name;
  private String description;
  private BigDecimal price;
  private int duration;
  private LocalDateTime createDate;
  private LocalDateTime lastUpdateDate;
}
