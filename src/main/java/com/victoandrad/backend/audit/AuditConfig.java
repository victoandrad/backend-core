package com.victoandrad.backend.audit;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AuditConfig {
}
