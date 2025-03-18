package com.maps.service;

import com.maps.persistence.MapStruct;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import com.maps.persistence.repository.RepositoryChartArea;
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
public class ServiceChartArea {

    private final RepositoryChartArea repositoryChartArea;

    public DTOResponseChartArea create(DTORequestChartArea created){
        return MapStruct.MAPPER.toDTO(repositoryChartArea.save(MapStruct.MAPPER.toObject(created)));
    }
    public Page<DTOResponseChartArea> retrieve(Pageable pageable, String value) {
        ChartArea object = new ChartArea();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<ChartArea> example = Example.of(object, exampleMatcher);
            return repositoryChartArea.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryChartArea.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryChartArea.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    public DTOResponseChartArea update(UUID id, DTORequestChartArea updated){
        return MapStruct.MAPPER.toDTO(repositoryChartArea.save(MapStruct.MAPPER.toObject(updated)));
    }
    public DTOResponseChartArea delete(UUID id){
        DTOResponseChartArea dtoResponseChartArea = MapStruct.MAPPER.toDTO(repositoryChartArea.findById(id).orElse(null));
        repositoryChartArea.deleteById(id);
        return dtoResponseChartArea;
    }
    public void delete() {
        repositoryChartArea.deleteAll();
    }

    public boolean existsByName(String value) {
        return repositoryChartArea.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        return repositoryChartArea.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}