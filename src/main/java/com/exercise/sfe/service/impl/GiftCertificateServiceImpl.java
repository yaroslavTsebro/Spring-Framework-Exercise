package com.exercise.sfe.service.impl;

import com.exercise.sfe.entity.GiftCertificate;
import com.exercise.sfe.repository.GiftCertificateRepository;
import com.exercise.sfe.service.GiftCertificateService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

  private GiftCertificateRepository giftCertificateRepository;

  public List<GiftCertificate> getAll() {
    return giftCertificateRepository.getAll();
  }

  public GiftCertificate update(GiftCertificate wantedGC) {
    var previousGC = giftCertificateRepository.getById(wantedGC.getId());
    if (previousGC.isEmpty()) {
      throw new RuntimeException();
    }
    giftCertificateRepository.update(previousGC.get(), wantedGC);
    return new GiftCertificate();
  }
}
