package com.maps.persistence.repository;

import com.maps.persistence.model.City;

import java.util.UUID;

public interface RepositoryCity extends RepositoryGeneric<City> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}