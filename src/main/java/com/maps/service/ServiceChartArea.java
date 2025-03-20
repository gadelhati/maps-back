package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import com.maps.persistence.repository.RepositoryChartArea;
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
public class ServiceChartArea extends ServiceGeneric<ChartArea, DTORequestChartArea, DTOResponseChartArea> {

    private final RepositoryChartArea repositoryChartArea;
    private final MapperInterface<ChartArea, DTORequestChartArea, DTOResponseChartArea> mapperInterface;

    public ServiceChartArea(RepositoryGeneric<ChartArea> repositoryGeneric, MapperInterface<ChartArea, DTORequestChartArea, DTOResponseChartArea> mapperInterface, RepositoryChartArea repositoryChartArea) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryChartArea = repositoryChartArea;
        this.mapperInterface = mapperInterface;
    }
    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryChartArea.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryChartArea.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}