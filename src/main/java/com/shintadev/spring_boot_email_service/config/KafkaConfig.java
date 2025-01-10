package com.shintadev.spring_boot_email_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class KafkaConfig {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
