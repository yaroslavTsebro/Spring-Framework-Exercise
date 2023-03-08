package com.exercise.sfe.mapper;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.dto.GiftCertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TagMapper.class)
public interface GiftCertificateMapper {

  @Mapping(source = "name", target = "name")
  @Mapping(source = "description", target = "description")
  @Mapping(source = "price", target = "price")
  @Mapping(source = "duration", target = "duration")
  @Mapping(source = "tags", target = "tags")
  GiftCertificate toGiftCertificate(GiftCertificateDto dto);

  GiftCertificateDto toGiftCertificateDto(GiftCertificate giftCertificate);
}
