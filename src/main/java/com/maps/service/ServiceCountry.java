package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.remodel.Country;
import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.persistence.payload.response.DTOResponseCountry;
import com.maps.persistence.repository.RepositoryCountry;
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
public class ServiceCountry extends ServiceGeneric<Country, DTORequestCountry, DTOResponseCountry> {

    private final RepositoryCountry repositoryCountry;
    private final MapperInterface<Country, DTORequestCountry, DTOResponseCountry> mapperInterface;

    public ServiceCountry(RepositoryGeneric<Country> repositoryGeneric, MapperInterface<Country, DTORequestCountry, DTOResponseCountry> mapperInterface, RepositoryCountry repositoryCountry) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryCountry = repositoryCountry;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryCountry.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryCountry.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}