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
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Service
public class ServiceResearch extends ServiceGeneric<Research, DTORequestResearch, DTOResponseResearch> {

    private final RepositoryResearch repositoryResearch;

    public ServiceResearch(Information information, RepositoryGeneric<Research> repositoryGeneric, MapperInterface<Research, DTORequestResearch, DTOResponseResearch> mapperInterface, RepositoryResearch repositoryResearch) {
        super(Research.class, information, repositoryGeneric, mapperInterface);
        this.repositoryResearch = repositoryResearch;
    }
}
