package com.zentry.zentrystore.infrastructure.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    // Habilita auditoría automática para @CreatedDate, @LastModifiedDate, etc.
}