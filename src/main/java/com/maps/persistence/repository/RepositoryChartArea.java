package com.maps.persistence.repository;

import com.maps.persistence.model.ChartArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryChartArea extends JpaRepository<ChartArea, UUID> {
    boolean existsByNameIgnoreCase(String value);
    boolean existsByNameIgnoreCaseAndIdNot(String number, UUID id);
    Page<ChartArea> findById(Pageable pageable, UUID uuid);
}