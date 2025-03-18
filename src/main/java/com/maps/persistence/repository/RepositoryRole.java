package com.maps.persistence.repository;

import com.maps.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface RepositoryRole extends RepositoryGeneric<Role> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Set<Role> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String username, UUID id);
    boolean existsByNameIgnoreCase(String value);
}