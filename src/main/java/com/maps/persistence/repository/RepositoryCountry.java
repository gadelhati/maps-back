package com.maps.persistence.repository;

import com.maps.persistence.model.Country;

import java.util.UUID;

public interface RepositoryCountry extends RepositoryGeneric<Country> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}