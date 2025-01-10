package com.shintadev.spring_boot_email_service.base;

import java.time.LocalDateTime;

public abstract class BaseEntity {

  private Long id;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
