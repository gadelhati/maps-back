package br.eti.gadelha.maps.security;

import br.eti.gadelha.maps.persistence.model.User;
import br.eti.gadelha.maps.persistence.repository.RepositoryUser;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<User> {

    private final RepositoryUser repositoryUser;

    public AuditorAwareImpl(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }
    @Override @NonNull
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return repositoryUser.findByUsername(authentication.getName());
    }
}