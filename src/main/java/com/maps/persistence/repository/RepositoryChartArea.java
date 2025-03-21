package com.maps.persistence.repository;

import com.maps.persistence.model.ChartArea;

import java.util.UUID;

public interface RepositoryChartArea extends RepositoryGeneric<ChartArea> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}