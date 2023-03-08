package com.exercise.sfe.controller;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.dto.GiftCertificateDto;
import com.exercise.sfe.entity.group.OnCreate;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.service.GiftCertificateService;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

  private final GiftCertificateService giftCertificateService;

  @GetMapping("/{id}")
  public GiftCertificate getById(@PathVariable @Min(1) Long id) {
    return giftCertificateService.getById(id);
  }

  @PostMapping
  public GiftCertificate create(@RequestBody @Validated(OnCreate.class) GiftCertificateDto giftCertificateDto) {
    return giftCertificateService.create(giftCertificateDto);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable @Min(1) Long id) {
    giftCertificateService.deleteById(id);
  }

  @GetMapping
  public List<GiftCertificate> getAllBySearchSettings(
      @RequestBody(required = false) SearchingSettings settings) {
      return giftCertificateService.getAllAccordingToSettings(settings);
  }

  @PutMapping("/{id}")
  public GiftCertificate updateById(@PathVariable @Min(1) Long id,
      @RequestBody GiftCertificateDto giftCertificateDto) {
    return giftCertificateService.update(giftCertificateDto, id);
  }
}
