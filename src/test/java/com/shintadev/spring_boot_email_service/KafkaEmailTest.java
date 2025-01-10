package com.shintadev.spring_boot_email_service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.shintadev.spring_boot_email_service.service.KafkaService;

@SpringBootTest
class KafkaEmailTest {

  @Autowired
  private KafkaService kafkaService;

  @Test
  void testKafkaProcessTextEmail() throws IOException {
    String messageContent = "{\"to\":\"hung112357@gmail.com\",\"subject\":\"Test Kafka\",\"body\":\"This is a test email.\"}";
    Message<String> kafkaMessage = MessageBuilder
        .withPayload(messageContent)
        .setHeader(KafkaHeaders.RECEIVED_TOPIC, "text-email-topic")
        .build();

    kafkaService.consumeMessage(
        kafkaMessage.getPayload(),
        "text-email-topic",
        null);
    assertDoesNotThrow(() -> kafkaService
        .consumeMessage(
            kafkaMessage.getPayload(),
            "text-email-topic",
            null));
  }
}
