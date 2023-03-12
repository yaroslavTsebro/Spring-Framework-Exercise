package com.exercise.sfe.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class GiftCertificateTag extends AbstractEntity {

  private Tag tag;
  private GiftCertificate giftCertificate;
}
