package com.exercise.sfe.controller;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.service.GiftCertificateService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gift-certificates")
@AllArgsConstructor
public class GiftCertificateController {

  private GiftCertificateService giftCertificateService;

  @GetMapping
  public List<GiftCertificate> getAll() {
    return giftCertificateService.getAll();
  }
}
