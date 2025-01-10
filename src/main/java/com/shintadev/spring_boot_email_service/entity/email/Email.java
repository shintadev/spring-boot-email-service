package com.shintadev.spring_boot_email_service.entity.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shintadev.spring_boot_email_service.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Email extends BaseEntity {

  @JsonProperty("to")
  private String to;

  @JsonProperty("subject")
  private String subject;

  @JsonProperty("body")
  private String body;

  @JsonProperty("attachmentName")
  private String attachmentName;

  @JsonProperty("attachmentData")
  private byte[] attachmentData;
}
