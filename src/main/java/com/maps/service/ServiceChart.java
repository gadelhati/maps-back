package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Chart;
import com.maps.persistence.payload.request.DTORequestChart;
import com.maps.persistence.payload.response.DTOResponseChart;
import com.maps.persistence.repository.RepositoryChart;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;

@Service
public class ServiceChart extends ServiceGeneric<Chart, DTORequestChart, DTOResponseChart> {

    private final RepositoryChart repositoryChart;
    private final MapperInterface<Chart, DTORequestChart, DTOResponseChart> mapperInterface;

    public ServiceChart(RepositoryGeneric<Chart> repositoryGeneric, MapperInterface<Chart, DTORequestChart, DTOResponseChart> mapperInterface, RepositoryChart repositoryChart) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryChart = repositoryChart;
        this.mapperInterface = mapperInterface;
    }
}