package com.exercise.sfe.mapper;

import com.exercise.sfe.entity.Tag;
import com.exercise.sfe.entity.dto.TagDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TagMapper {

  @Mapping(source = "name", target = "name")
  Tag toTag(TagDto dto);

  @InheritInverseConfiguration
  TagDto toTagDto(Tag tag);
}
