package com.shintadev.spring_boot_email_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.spring_boot_email_service.entity.email.Email;
import com.shintadev.spring_boot_email_service.service.EmailService;

class KafkaServiceImplTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private EmailService emailService;

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  @InjectMocks
  private KafkaServiceImpl kafkaServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testConsumeMessage_TextEmailTopic() throws IOException {
    String messageContent = "{\"to\":\"test@example.com\",\"subject\":\"Test Kafka\",\"body\":\"This is a test email.\"}";
    Email email = new Email();
    email.setTo("test@example.com");
    email.setSubject("Test Kafka");
    email.setBody("This is a test email.");

    when(objectMapper.readValue(messageContent, Email.class)).thenReturn(email);

    Acknowledgment ack = mock(Acknowledgment.class);

    assertDoesNotThrow(() -> kafkaServiceImpl.consumeMessage(messageContent, "text-email-topic", ack));
    verify(emailService, times(1)).sendTextEmail(email);
    verify(ack, times(1)).acknowledge();
  }

  @Test
  void testConsumeMessage_HtmlEmailTopic() throws IOException {
    String messageContent = "{\"to\":\"test@example.com\",\"subject\":\"Test Kafka\",\"body\":\"<h1>This is a test email.</h1>\"}";
    Email email = new Email();
    email.setTo("test@example.com");
    email.setSubject("Test Kafka");
    email.setBody("<h1>This is a test email.</h1>");

    when(objectMapper.readValue(messageContent, Email.class)).thenReturn(email);

    Acknowledgment ack = mock(Acknowledgment.class);

    assertDoesNotThrow(() -> kafkaServiceImpl.consumeMessage(messageContent, "html-email-topic", ack));
    verify(emailService, times(1)).sendHtmlEmail(email);
    verify(ack, times(1)).acknowledge();
  }

  @Test
  void testConsumeMessage_EmailWithAttachmentTopic() throws IOException {
    String messageContent = "{\"to\":\"test@example.com\",\"subject\":\"Test Kafka\",\"body\":\"This is a test email with attachment.\"}";
    Email email = new Email();
    email.setTo("test@example.com");
    email.setSubject("Test Kafka");
    email.setBody("This is a test email with attachment.");

    when(objectMapper.readValue(messageContent, Email.class)).thenReturn(email);

    Acknowledgment ack = mock(Acknowledgment.class);

    assertDoesNotThrow(() -> kafkaServiceImpl.consumeMessage(messageContent, "email-with-attachment-topic", ack));
    verify(emailService, times(1)).sendEmailWithAttachment(email);
    verify(ack, times(1)).acknowledge();
  }

  @Test
  void testConsumeMessage_IOException() throws IOException {
    String messageContent = "invalid message content";

    when(objectMapper.readValue(messageContent, Email.class)).thenThrow(new IOException());

    Acknowledgment ack = mock(Acknowledgment.class);

    assertDoesNotThrow(() -> kafkaServiceImpl.consumeMessage(messageContent, "text-email-topic", ack));
    verify(kafkaTemplate, times(1)).send("dead-letter-email-topic", messageContent);
  }

  @Test
  void testConsumeMessage_Exception() throws IOException {
    String messageContent = "{\"to\":\"test@example.com\",\"subject\":\"Test Kafka\",\"body\":\"This is a test email.\"}";
    Email email = new Email();
    email.setTo("test@example.com");
    email.setSubject("Test Kafka");
    email.setBody("This is a test email.");

    when(objectMapper.readValue(messageContent, Email.class)).thenReturn(email);
    doThrow(new RuntimeException()).when(emailService).sendTextEmail(email);

    Acknowledgment ack = mock(Acknowledgment.class);

    assertDoesNotThrow(() -> kafkaServiceImpl.consumeMessage(messageContent, "text-email-topic", ack));
    verify(kafkaTemplate, times(1)).send("dead-letter-email-topic", messageContent);
  }
}