package com.shintadev.spring_boot_email_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

  @Value("${spring.mail.username}")
  private String from;

  @Autowired
  private JavaMailSender javaMailSender;

  public void sendTextEmail(String to, String subject, String body) {
    // TODO: code to send email
  }

  public void sendHtmlEmail(String to, String subject, String body) {
    // TODO: code to send email
  }

  public void sendEmailWithAttachment(String to, String subject, String body, String attachment) {
    // TODO: code to send email
  }
}
