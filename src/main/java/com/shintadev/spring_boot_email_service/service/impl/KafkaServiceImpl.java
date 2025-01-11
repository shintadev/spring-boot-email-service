package com.shintadev.spring_boot_email_service.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.spring_boot_email_service.entity.email.Email;
import com.shintadev.spring_boot_email_service.service.EmailService;
import com.shintadev.spring_boot_email_service.service.KafkaService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmailService emailService;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  @KafkaListener(topics = { "text-email-topic", "html-email-topic",
      "email-with-attachment-topic" }, groupId = "email-group")
  public void consumeMessage(
      String message,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      Acknowledgment ack) {
    try {
      Email email = parseEmailMessage(message);

      switch (topic) {
        case "text-email-topic":
          emailService.sendTextEmail(email);
          break;

        case "html-email-topic":
          emailService.sendHtmlEmail(email);
          break;

        case "email-with-attachment-topic":
          emailService.sendEmailWithAttachment(email);
          break;

        default:
          throw new IllegalArgumentException("Unknown topic: " + topic);
      }

      log.info("Consumed message: {}", message);
      if (ack != null) {
        ack.acknowledge();
      }
    } catch (Exception e) {
      kafkaTemplate.send("dead-letter-email-topic", message);
      log.error("Failed to process message: Error {}", e.getMessage());
    }
  }

  @Override
  public Email parseEmailMessage(String message) throws IOException {
    return objectMapper.readValue(message, Email.class);
  }

}