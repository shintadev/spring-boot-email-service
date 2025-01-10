package com.shintadev.spring_boot_email_service.service;

public interface EmailService {

  String sendTextEmail(String to, String subject, String text);

  String sendHtmlEmail(String to, String subject, String html);

  String sendEmailWithAttachment(String to, String subject, String text, String attachment);
}
