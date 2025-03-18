package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.GaugeStation;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.persistence.payload.response.DTOResponseGaugeStation;
import com.maps.persistence.repository.RepositoryGaugeStation;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class ServiceGaugeStation extends ServiceGeneric<GaugeStation, DTORequestGaugeStation, DTOResponseGaugeStation> {

    private final RepositoryGaugeStation repositoryGaugeStation;
    private final MapperInterface<GaugeStation, DTORequestGaugeStation, DTOResponseGaugeStation> mapperInterface;

    public ServiceGaugeStation(RepositoryGeneric<GaugeStation> repositoryGeneric, MapperInterface<GaugeStation, DTORequestGaugeStation, DTOResponseGaugeStation> mapperInterface, RepositoryGaugeStation repositoryGaugeStation) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryGaugeStation = repositoryGaugeStation;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByNumber(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryGaugeStation.existsByNumberIgnoreCase(value);
    }
    public boolean existsByNumberAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryGaugeStation.existsByNumberIgnoreCaseAndIdNot(value, id);
    }
}