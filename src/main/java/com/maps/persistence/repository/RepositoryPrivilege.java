package com.maps.persistence.repository;

import com.maps.persistence.model.Privilege;

import java.util.Set;
import java.util.UUID;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

public interface RepositoryPrivilege extends RepositoryGeneric<Privilege> {

    Set<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
