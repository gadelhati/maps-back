package com.maps.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

public interface RepositoryInterface<T> {

    T findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
    Page<T> findById(Pageable pageable, UUID uuid);
    Page<T> findByIdOrderByIdAsc(Pageable pageable, UUID id);
    Page<T> findByNameContainingIgnoreCaseOrderByNameAsc(Pageable pageable, String name);
}
