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

  private static final long serialVersionUID = 1976295386048222544L;
  private Tag tag;
  private GiftCertificate giftCertificate;
}
