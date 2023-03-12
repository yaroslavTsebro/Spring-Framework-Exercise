package com.exercise.sfe.entity.dto;


import com.exercise.sfe.entity.group.OnCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class GiftCertificateDto {

  @NotNull(groups = {OnCreate.class})
  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String name;

  @NotNull(groups = {OnCreate.class})
  @Pattern(regexp = "[\\w\\s]{2,256}+")
  private String description;

  @NotNull(groups = {OnCreate.class})
  @DecimalMin(value = "0.1")
  @Digits(integer = 12, fraction = 2)
  private BigDecimal price;

  @NotNull(groups = {OnCreate.class})
  @Min(value = 1)
  @Max(value = 365)
  private int duration;

  private Set<@Valid TagDto> tags;
}
