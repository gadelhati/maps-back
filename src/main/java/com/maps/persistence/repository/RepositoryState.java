package com.maps.persistence.repository;

import com.maps.persistence.model.remodel.State;

import java.util.UUID;

public interface RepositoryState extends RepositoryGeneric<State> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}