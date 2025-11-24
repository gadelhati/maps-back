package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Researcher;
import com.maps.persistence.payload.request.DTORequestResearcher;
import com.maps.persistence.payload.response.DTOResponseResearcher;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryResearcher;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Service
public class ServiceResearcher extends ServiceGeneric<Researcher, DTORequestResearcher, DTOResponseResearcher> {

    private final RepositoryResearcher repositoryResearcher;

    public ServiceResearcher(Information information, RepositoryGeneric<Researcher> repositoryGeneric, MapperInterface<Researcher, DTORequestResearcher, DTOResponseResearcher> mapperInterface, RepositoryResearcher repositoryResearcher) {
        super(Researcher.class, information, repositoryGeneric, mapperInterface);
        this.repositoryResearcher = repositoryResearcher;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryResearcher.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryResearcher.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}
