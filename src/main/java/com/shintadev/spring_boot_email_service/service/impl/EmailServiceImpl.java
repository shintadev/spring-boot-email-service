package com.shintadev.spring_boot_email_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shintadev.spring_boot_email_service.service.EmailService;
import com.shintadev.spring_boot_email_service.util.EmailUtil;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private EmailUtil emailUtil;

  @Override
  public String sendTextEmail(String to, String subject, String text) {
    return "Email sent to " + to + " with subject " + subject + " and text " + text;
  }

  @Override
  public String sendHtmlEmail(String to, String subject, String html) {
    return "Email sent to " + to + " with subject " + subject + " and html " + html;
  }

  @Override
  public String sendEmailWithAttachment(String to, String subject, String text, String attachment) {
    return "Email sent to " + to + " with subject " + subject + " and text " + text + " and attachment " + attachment;
  }
}
