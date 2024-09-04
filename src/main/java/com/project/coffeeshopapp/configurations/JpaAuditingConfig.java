package com.project.coffeeshopapp.configurations;

import com.project.coffeeshopapp.models.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class JpaAuditingConfig {
    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
    public class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails userDetails) {
                String fullName = userDetails.getUser().getFullName();
                String auditorInfo = userDetails.getUsername() + " - " + (fullName != null ? fullName : "");
                return Optional.of(auditorInfo);
            } else {
                return Optional.empty();
            }
        }
    }
}
