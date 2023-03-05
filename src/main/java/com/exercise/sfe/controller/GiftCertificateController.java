package com.exercise.sfe.controller;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.entity.search.SearchingSettings;
import com.exercise.sfe.service.GiftCertificateService;
import java.util.List;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GiftCertificateController {

  private GiftCertificateService giftCertificateService;

  @GetMapping("/{id}")
  public GiftCertificate getById(@PathVariable Long id) {
    return giftCertificateService.getById(id);
  }

  @PostMapping
  public GiftCertificate create(@RequestBody GiftCertificate gc) {
    return giftCertificateService.create(gc);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    giftCertificateService.deleteById(id);
  }

  @GetMapping()
  public List<GiftCertificate> getAllBySearchSettings(
      @RequestBody(required = false) SearchingSettings settings) {
    if (settings == null) {
      return giftCertificateService.getAll();
    } else {
      return giftCertificateService.getAll(settings);
    }
  }

  @PutMapping("/{id}")
  public GiftCertificate updateById(@PathVariable Long id,
      @RequestBody GiftCertificate certificate) {
    certificate.setId(id);
    return giftCertificateService.update(certificate);
  }
}
