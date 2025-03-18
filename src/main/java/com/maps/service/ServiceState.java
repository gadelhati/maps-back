package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.State;
import com.maps.persistence.payload.request.DTORequestState;
import com.maps.persistence.payload.response.DTOResponseState;
import com.maps.persistence.repository.RepositoryState;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class ServiceState extends ServiceGeneric<State, DTORequestState, DTOResponseState> {

    private final RepositoryState repositoryState;
    private final MapperInterface<State, DTORequestState, DTOResponseState> mapperInterface;

    public ServiceState(RepositoryGeneric<State> repositoryGeneric, MapperInterface<State, DTORequestState, DTOResponseState> mapperInterface, RepositoryState repositoryState) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryState = repositoryState;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryState.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryState.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}