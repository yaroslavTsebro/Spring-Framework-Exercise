package com.exercise.sfe.entity;

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
  private Tag tag;
  private GiftCertificate giftCertificate;
}
