package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Research;
import com.maps.persistence.payload.request.DTORequestResearch;
import com.maps.persistence.payload.response.DTOResponseResearch;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryResearch;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
public class ServiceResearch extends ServiceGeneric<Research, DTORequestResearch, DTOResponseResearch> {

    private final RepositoryResearch repositoryResearch;
    private final MapperInterface<Research, DTORequestResearch, DTOResponseResearch> mapperInterface;

    public ServiceResearch(RepositoryGeneric<Research> repositoryGeneric, MapperInterface<Research, DTORequestResearch, DTOResponseResearch> mapperInterface, RepositoryResearch repositoryResearch) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryResearch = repositoryResearch;
        this.mapperInterface = mapperInterface;
    }
}