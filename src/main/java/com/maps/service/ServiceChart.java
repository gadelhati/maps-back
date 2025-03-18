package com.maps.service;

import com.maps.persistence.MapStruct;
import com.maps.persistence.model.Chart;
import com.maps.persistence.payload.request.DTORequestChart;
import com.maps.persistence.payload.response.DTOResponseChart;
import com.maps.persistence.repository.RepositoryChart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public class ServiceChart {

    private final RepositoryChart repositoryChart;

    public DTOResponseChart create(DTORequestChart created){
        return MapStruct.MAPPER.toDTO(repositoryChart.save(MapStruct.MAPPER.toObject(created)));
    }
    public Page<DTOResponseChart> retrieve(Pageable pageable, String value) {
        Chart object = new Chart();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<Chart> example = Example.of(object, exampleMatcher);
            return repositoryChart.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryChart.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryChart.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    public DTOResponseChart update(UUID id, DTORequestChart updated){
        return MapStruct.MAPPER.toDTO(repositoryChart.save(MapStruct.MAPPER.toObject(updated)));
    }
    public DTOResponseChart delete(UUID id){
        DTOResponseChart dtoResponseChart = MapStruct.MAPPER.toDTO(repositoryChart.findById(id).orElse(null));
        repositoryChart.deleteById(id);
        return dtoResponseChart;
    }
    public void delete() {
        repositoryChart.deleteAll();
    }

    public boolean existsByNumber(String value) {
        return repositoryChart.existsByNumberIgnoreCase(value);
    }
    public boolean existsByNumberAndIdNot(String value, UUID id) {
        return repositoryChart.existsByNumberIgnoreCaseAndIdNot(value, id);
    }
}