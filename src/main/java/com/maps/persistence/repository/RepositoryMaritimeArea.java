package com.maps.persistence.repository;

import com.maps.persistence.model.MaritimeArea;

import java.util.UUID;

public interface RepositoryMaritimeArea extends RepositoryGeneric<MaritimeArea> {

    boolean existsByNameIgnoreCase(String value);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}