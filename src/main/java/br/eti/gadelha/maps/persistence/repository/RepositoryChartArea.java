package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.ChartArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryChartArea extends JpaRepository<ChartArea, UUID> {
    boolean existsByNameIgnoreCase(String value);
    boolean existsByNameIgnoreCaseAndIdNot(String number, UUID id);
}