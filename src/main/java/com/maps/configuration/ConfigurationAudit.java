package com.maps.configuration;

import com.maps.persistence.model.User;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.service.ServiceAuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ConfigurationAudit {
    private final RepositoryUser repositoryUser;

    public ConfigurationAudit(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }
    @Bean
    public AuditorAware<User> auditorAware() {
        return new ServiceAuditorAwareImpl(repositoryUser);
    }
}