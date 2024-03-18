package br.eti.gadelha.maps.configuration;

import br.eti.gadelha.maps.persistence.model.User;
import br.eti.gadelha.maps.security.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration @EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ConfigurationAudit {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AuditorAwareImpl();
    }
}