package com.maps.persistence.repository;

import com.maps.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends RepositoryGeneric<User> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<User> findByUsername(String name);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);
    boolean existsByUsernameIgnoreCase(String value);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, UUID id);
    Page<User> findById(Pageable pageable, UUID uuid);
}