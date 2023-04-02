package com.exercise.sfe.entity.search;

import jakarta.validation.constraints.Pattern;
import java.util.Map;
import lombok.Data;

@Data
public class SearchingSettings {

  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String tagName;

  @Pattern(regexp = "[\\w\\s]{2,128}+")
  private String giftCertificateName;

  @Pattern(regexp = "[\\w\\s]{2,512}+")
  private String giftCertificateDescription;

  private Map<String, @Pattern(regexp = "ASC|DESC") SortingTypes> sortingMap;
}
