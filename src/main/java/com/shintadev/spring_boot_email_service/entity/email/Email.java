package com.shintadev.spring_boot_email_service.entity.email;

import com.shintadev.spring_boot_email_service.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Email extends BaseEntity {

  private String to;
  private String subject;
  private String body;
  private String attachment;
}
