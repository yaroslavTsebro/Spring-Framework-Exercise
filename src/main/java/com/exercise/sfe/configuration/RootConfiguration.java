package com.exercise.sfe.configuration;

import com.exercise.sfe.mapper.GiftCertificateMapper;
import com.exercise.sfe.mapper.TagMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan(basePackages = "com.exercise.sfe", excludeFilters = {
    @ComponentScan.Filter(EnableWebMvc.class),
    @ComponentScan.Filter(RestController.class)
})
public class RootConfiguration {

  @Bean
  protected TagMapper tagMapper() {
    return Mappers.getMapper(TagMapper.class);
  }

  @Bean
  protected GiftCertificateMapper giftCertificateMapper() {
    return Mappers.getMapper(GiftCertificateMapper.class);
  }
}