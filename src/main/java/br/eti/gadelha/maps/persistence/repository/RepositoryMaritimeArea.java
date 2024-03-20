package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.MaritimeArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryMaritimeArea extends JpaRepository<MaritimeArea, UUID>, RepositoryInterface<MaritimeArea> {

}