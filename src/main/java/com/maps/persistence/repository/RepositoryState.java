package com.maps.persistence.repository;

import com.maps.persistence.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryState extends JpaRepository<State, UUID>, RepositoryInterface<State> {

}