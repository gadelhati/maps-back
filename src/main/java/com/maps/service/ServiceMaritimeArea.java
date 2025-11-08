package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.MaritimeArea;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.persistence.payload.response.DTOResponseMaritimeArea;
import com.maps.persistence.repository.RepositoryMaritimeArea;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Service
public class ServiceMaritimeArea extends ServiceGeneric<MaritimeArea, DTORequestMaritimeArea, DTOResponseMaritimeArea> {

    private final RepositoryMaritimeArea repositoryMaritimeArea;

    public ServiceMaritimeArea(Information information, RepositoryGeneric<MaritimeArea> repositoryGeneric, MapperInterface<MaritimeArea, DTORequestMaritimeArea, DTOResponseMaritimeArea> mapperInterface, RepositoryMaritimeArea repositoryMaritimeArea) {
        super(MaritimeArea.class, information, repositoryGeneric, mapperInterface);
        this.repositoryMaritimeArea = repositoryMaritimeArea;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryMaritimeArea.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryMaritimeArea.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}
