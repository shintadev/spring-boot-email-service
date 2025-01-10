package com.shintadev.spring_boot_email_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shintadev.spring_boot_email_service.entity.email.Email;
import com.shintadev.spring_boot_email_service.service.EmailService;
import com.shintadev.spring_boot_email_service.util.EmailUtil;


@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private EmailUtil emailUtil;

  @Override
  public String sendTextEmail(Email email) {
    emailUtil.sendTextEmail(email.getTo(), email.getSubject(), email.getBody());
    return "Email sent to " + email.getTo() + " with subject " + email.getSubject() + " and text " + email.getBody();
  }

  @Override
  public String sendHtmlEmail(Email email) {
    emailUtil.sendHtmlEmail(email.getTo(), email.getSubject(), email.getBody());
    return "Email sent to " + email.getTo() + " with subject " + email.getSubject() + " and html " + email.getBody();
  }

  @Override
  public String sendEmailWithAttachment(Email email) {
    emailUtil.sendEmailWithAttachment(email.getTo(), email.getSubject(), email.getBody(), email.getAttachmentName(),
        email.getAttachmentData());
    return "Email sent to " + email.getTo() + " with subject " + email.getSubject() + " and text " + email.getBody()
        + " and attachment " + email.getAttachmentName();
  }

  @Override
  public String sendEmailWithMultipleAttachments(Email email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithMultipleAttachments'");
  }
}
