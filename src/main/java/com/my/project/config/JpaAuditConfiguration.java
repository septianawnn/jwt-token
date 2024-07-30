package com.my.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author pi
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
    return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
     */

        return () -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
//        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Optional.ofNullable(principal.getClaimAsString("userName"));
                return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            } else {
                return Optional.ofNullable("Unknown");
            }
        };
    }
}
