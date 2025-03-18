package com.maps.persistence.repository;

import com.maps.persistence.model.Chart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface RepositoryChart extends JpaRepository<Chart, UUID> {
    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
    Page<Chart> findById(Pageable pageable, UUID uuid);
}