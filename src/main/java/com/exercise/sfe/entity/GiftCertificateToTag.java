package com.exercise.sfe.entity;

import jakarta.validation.Valid;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class GiftCertificateToTag implements Serializable {

  private static final long serialVersionUID = 2L;
  @Valid
  private Tag tag;
  @Valid
  private GiftCertificate giftCertificate;
}
