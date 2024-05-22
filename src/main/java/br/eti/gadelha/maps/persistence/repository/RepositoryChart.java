package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.Chart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryChart extends JpaRepository<Chart, UUID> {
    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
    Page<Chart> findById(Pageable pageable, UUID uuid);
}