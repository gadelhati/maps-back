package com.maps.persistence.repository;

import com.maps.persistence.model.GaugeStation;

import java.util.UUID;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

public interface RepositoryGaugeStation extends RepositoryGeneric<GaugeStation> {

    boolean existsByNumberIgnoreCase(String number);
    boolean existsByNumberIgnoreCaseAndIdNot(String number, UUID id);
}
