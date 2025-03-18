package com.maps.service;

import com.maps.persistence.MapStruct;
import com.maps.persistence.model.GaugeStation;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.persistence.payload.response.DTOResponseGaugeStation;
import com.maps.persistence.repository.RepositoryGaugeStation;
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
public class ServiceGaugeStation {

    private final RepositoryGaugeStation repositoryGaugeStation;

    public DTOResponseGaugeStation create(DTORequestGaugeStation created){
        return MapStruct.MAPPER.toDTO(repositoryGaugeStation.save(MapStruct.MAPPER.toObject(created)));
    }
    public Page<DTOResponseGaugeStation> retrieve(Pageable pageable, String value) {
        GaugeStation object = new GaugeStation();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<GaugeStation> example = Example.of(object, exampleMatcher);
            return repositoryGaugeStation.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryGaugeStation.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryGaugeStation.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    public DTOResponseGaugeStation update(UUID id, DTORequestGaugeStation updated){
        return MapStruct.MAPPER.toDTO(repositoryGaugeStation.save(MapStruct.MAPPER.toObject(updated)));
    }
    public DTOResponseGaugeStation delete(UUID id){
        DTOResponseGaugeStation dtoResponseGaugeStation = MapStruct.MAPPER.toDTO(repositoryGaugeStation.findById(id).orElse(null));
        repositoryGaugeStation.deleteById(id);
        return dtoResponseGaugeStation;
    }
    public void delete() {
        repositoryGaugeStation.deleteAll();
    }

    public boolean existsByNumber(String value) {
        return repositoryGaugeStation.existsByNumberIgnoreCase(value);
    }
    public boolean existsByNumberAndIdNot(String value, UUID id) {
        return repositoryGaugeStation.existsByNumberIgnoreCaseAndIdNot(value, id);
    }
}