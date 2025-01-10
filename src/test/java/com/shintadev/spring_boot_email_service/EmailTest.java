package com.shintadev.spring_boot_email_service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shintadev.spring_boot_email_service.entity.email.Email;
import com.shintadev.spring_boot_email_service.service.EmailService;

@SpringBootTest
public class EmailTest {

  private final String to = "hung112357@gmail.com";
  private final String subject = "Test Email";
  private final String body = "This is a test email.";

  @Autowired
  private EmailService emailService;

  @Autowired
  private TemplateEngine templateEngine;

  @Test
  void sendTextEmail() {
    Email email = new Email();

    email.setTo(to);
    email.setSubject(subject);
    email.setBody(body);

    // emailService.sendTextEmail(email);
    assertDoesNotThrow(() -> emailService.sendTextEmail(email));
  }

  @Test
  void sendHtmlEmail() throws Exception {
    Email email = new Email();

    email.setTo(to);
    email.setSubject(subject);

    Context context = new Context();
    context.setVariable("subject", subject);
    context.setVariable("body", body);

    String htmlBody = templateEngine.process("email/test-mail", context);
    email.setBody(htmlBody);

    // emailService.sendHtmlEmail(email);
    assertDoesNotThrow(() -> emailService.sendHtmlEmail(email));
  }

  @Test
  void sendEmailWithAttachment() throws Exception {
    Email email = new Email();

    Resource resource = new ClassPathResource("/templates/email/test-mail.html");
    String attachmentName = resource.getFilename();
    byte[] attachmentData = resource.getInputStream().readAllBytes();
    email.setTo(to);
    email.setSubject(subject);
    email.setBody(body);
    email.setAttachmentName(attachmentName);
    email.setAttachmentData(attachmentData);

    // emailService.sendEmailWithAttachment(email);
    assertDoesNotThrow(() -> emailService.sendEmailWithAttachment(email));
  }
}
