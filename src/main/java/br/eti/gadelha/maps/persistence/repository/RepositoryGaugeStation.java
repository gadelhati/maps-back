package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.GaugeStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryGaugeStation extends JpaRepository<GaugeStation, UUID> {

    boolean existsByNumberIgnoreCase(String value);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
}