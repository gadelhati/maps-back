package com.maps.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface ServiceInterface<T, I, O> {
    O create(I created);
    Page<O> retrieve(Pageable pageable, String value, Class<T> entityClass);
    Optional<O> retrieve(UUID id, Class<T> entityClass);
    O update(I updated);
    O delete(UUID id);
//    void delete();
}