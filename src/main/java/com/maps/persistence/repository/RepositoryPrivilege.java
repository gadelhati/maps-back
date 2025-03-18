package com.maps.persistence.repository;

import com.maps.persistence.model.Privilege;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

public interface RepositoryPrivilege extends RepositoryGeneric<Privilege> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Set<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String username, UUID id);
    boolean existsByNameIgnoreCase(String value);
}