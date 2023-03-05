package com.exercise.sfe.entity;

import com.exercise.sfe.entity.group.CreateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GiftCertificate extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 3L;

  @NotNull(groups = CreateGroup.class)
  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String name;

  @NotNull(groups = CreateGroup.class)
  @Pattern(regexp = "[\\w\\s]{2,256}+")
  private String description;

  @NotNull(groups = CreateGroup.class)
  @DecimalMin(value = "0.1")
  @Digits(integer = 12, fraction = 2)
  private BigDecimal price;

  @NotNull(groups = CreateGroup.class)
  @Min(value = 1)
  @Max(value = 365)
  private int duration;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime lastUpdateDate;

  private List<@Valid Tag> tags;
}
