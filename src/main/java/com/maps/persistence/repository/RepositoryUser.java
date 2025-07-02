package com.maps.persistence.repository;

import com.maps.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends RepositoryGeneric<User> {

    Optional<User> findByUsername(String username);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, UUID id);
    Page<User> findById(Pageable pageable, UUID uuid);
}