package com.shintadev.spring_boot_email_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailUtil {

  final int MAX_ATTACHMENT_SIZE = 10 * 1024 * 1024; // 10MB

  @Value("${spring.mail.username}")
  private String from;

  @Autowired
  private JavaMailSender javaMailSender;

  public void sendTextEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);

    try {
      javaMailSender.send(message);
      log.info("Email sent successfully");
    } catch (Exception e) {
      log.error("Error sending email: {}", e.getMessage());
      throw new RuntimeException("Failed to send email");
    }
  }

  public void sendHtmlEmail(String to, String subject, String body) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true);
      helper.setFrom(from);

      javaMailSender.send(message);
      log.info("Html email sent successfully");
    } catch (Exception e) {
      log.error("Error sending Html email: {}", e.getMessage());
      throw new RuntimeException("Failed to send email");
    }
  }

  public void sendEmailWithAttachment(String to, String subject, String body, String attachmentName,
      byte[] attachmentData) {
    if (attachmentData.length > MAX_ATTACHMENT_SIZE) {
      log.error("Attachment size is exceeds {} bytes limit: {} bytes", MAX_ATTACHMENT_SIZE, attachmentData.length);
      throw new RuntimeException("Attachment size is too large");
    }
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true);
      helper.setFrom(from);

      // Add attachment
      ByteArrayResource attachment = new ByteArrayResource(attachmentData);
      helper.addAttachment(attachmentName, attachment);

      javaMailSender.send(message);
      log.info("Email with attachment sent successfully");
    } catch (Exception e) {
      log.error("Error sending email with attachment: {}", e.getMessage());
      throw new RuntimeException("Failed to send email with attachment");
    }
  }
}
