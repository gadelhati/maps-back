package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.InternationalChart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryInternationalChart extends JpaRepository<InternationalChart, UUID> {

    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
}