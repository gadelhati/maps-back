package com.maps.persistence.repository;

import com.maps.persistence.model.InternationalChart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryInternationalChart extends JpaRepository<InternationalChart, UUID> {

    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
    Page<InternationalChart> findById(Pageable pageable, UUID uuid);
}