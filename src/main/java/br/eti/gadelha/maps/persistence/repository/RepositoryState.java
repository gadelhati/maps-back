package br.eti.gadelha.maps.persistence.repository;

import br.eti.gadelha.maps.persistence.model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryState extends JpaRepository<State, UUID>, RepositoryInterface<State> {

}