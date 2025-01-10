package com.shintadev.spring_boot_email_service.service;

import java.io.IOException;

import org.springframework.kafka.support.Acknowledgment;

import com.shintadev.spring_boot_email_service.entity.email.Email;

public interface KafkaService {

  void consumeMessage(String message, String topic, Acknowledgment ack);

  Email parseEmailMessage(String message) throws IOException;
}
