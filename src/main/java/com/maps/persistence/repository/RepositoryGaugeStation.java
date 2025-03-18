package com.maps.persistence.repository;

import com.maps.persistence.model.GaugeStation;

import java.util.UUID;

public interface RepositoryGaugeStation extends RepositoryGeneric<GaugeStation> {

    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
}