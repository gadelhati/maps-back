package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryPrivilege;
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
public class ServicePrivilege extends ServiceGeneric<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    private final RepositoryPrivilege repositoryPrivilege;
    private final MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface;

    public ServicePrivilege(RepositoryGeneric<Privilege> repositoryGeneric, MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface, RepositoryPrivilege repositoryPrivilege) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryPrivilege = repositoryPrivilege;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryPrivilege.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryPrivilege.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}