package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryCountry extends JpaRepository<Country, UUID>, RepositoryInterface<Country> {

}