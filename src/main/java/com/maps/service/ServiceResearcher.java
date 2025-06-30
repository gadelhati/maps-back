package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.remodel.Researcher;
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
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
public class ServiceResearcher extends ServiceGeneric<Researcher, DTORequestResearcher, DTOResponseResearcher> {

    private final RepositoryResearcher repositoryResearcher;
    private final MapperInterface<Researcher, DTORequestResearcher, DTOResponseResearcher> mapperInterface;

    public ServiceResearcher(RepositoryGeneric<Researcher> repositoryGeneric, MapperInterface<Researcher, DTORequestResearcher, DTOResponseResearcher> mapperInterface, RepositoryResearcher repositoryResearcher) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryResearcher = repositoryResearcher;
        this.mapperInterface = mapperInterface;
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