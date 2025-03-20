package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.City;
import com.maps.persistence.payload.request.DTORequestCity;
import com.maps.persistence.payload.response.DTOResponseCity;
import com.maps.persistence.repository.RepositoryCity;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
public class ServiceCity extends ServiceGeneric<City, DTORequestCity, DTOResponseCity> {

    private final RepositoryCity repositoryCity;
    private final MapperInterface<City, DTORequestCity, DTOResponseCity> mapperInterface;

    public ServiceCity(RepositoryGeneric<City> repositoryGeneric, MapperInterface<City, DTORequestCity, DTOResponseCity> mapperInterface, RepositoryCity repositoryCity) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryCity = repositoryCity;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryCity.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryCity.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}