package com.maps.persistence.repository;

import com.maps.persistence.model.remodel.Country;

import java.util.UUID;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

public interface RepositoryCountry extends RepositoryGeneric<Country> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
