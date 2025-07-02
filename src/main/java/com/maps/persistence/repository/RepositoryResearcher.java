package com.maps.persistence.repository;

import com.maps.persistence.model.remodel.Researcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface RepositoryResearcher extends RepositoryGeneric<Researcher> {

    Set<Researcher> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
    boolean existsByNameIgnoreCase(String name);
}