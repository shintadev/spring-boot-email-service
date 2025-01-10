package com.shintadev.spring_boot_email_service.service;

import com.shintadev.spring_boot_email_service.entity.email.Email;

public interface EmailService {

  String sendTextEmail(Email email);

  String sendHtmlEmail(Email email);

  String sendEmailWithAttachment(Email email);

  String sendEmailWithMultipleAttachments(Email email);
}
