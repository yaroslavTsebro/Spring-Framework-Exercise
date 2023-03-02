package com.exercise.sfe.configuration;

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

}