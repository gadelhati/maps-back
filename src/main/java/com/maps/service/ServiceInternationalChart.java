package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.InternationalChart;
import com.maps.persistence.payload.request.DTORequestInternationalChart;
import com.maps.persistence.payload.response.DTOResponseInternationalChart;
import com.maps.persistence.repository.RepositoryInternationalChart;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import org.springframework.stereotype.Service;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Service
public class ServiceInternationalChart extends ServiceGeneric<InternationalChart, DTORequestInternationalChart, DTOResponseInternationalChart> {

    private final RepositoryInternationalChart repositoryInternationalChart;

    public ServiceInternationalChart(Information information, RepositoryGeneric<InternationalChart> repositoryGeneric, MapperInterface<InternationalChart, DTORequestInternationalChart, DTOResponseInternationalChart> mapperInterface, RepositoryInternationalChart repositoryInternationalChart) {
        super(InternationalChart.class, information, repositoryGeneric, mapperInterface);
        this.repositoryInternationalChart = repositoryInternationalChart;
    }
}
