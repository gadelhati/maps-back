package com.maps.persistence.repository;

import com.maps.persistence.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface RepositoryToken extends RepositoryGeneric<Token> {

    Page<Token> findById(Pageable pageable, UUID uuid);
    Optional<Token> findByRefreshToken(UUID uuid);
    boolean existsByRefreshToken(UUID refreshToken);
}