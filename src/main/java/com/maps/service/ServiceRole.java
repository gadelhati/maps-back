package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.payload.response.DTOResponseRole;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryRole;
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
public class ServiceRole extends ServiceGeneric<Role, DTORequestRole, DTOResponseRole> {

    private final RepositoryRole repositoryRole;
    private final MapperInterface<Role, DTORequestRole, DTOResponseRole> mapperInterface;

    public ServiceRole(RepositoryGeneric<Role> repositoryGeneric, MapperInterface<Role, DTORequestRole, DTOResponseRole> mapperInterface, RepositoryRole repositoryRole) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryRole = repositoryRole;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryRole.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryRole.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}