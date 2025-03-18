package com.maps.service;

import com.maps.persistence.MapStruct;
import com.maps.persistence.model.MaritimeArea;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.persistence.payload.response.DTOResponseMaritimeArea;
import com.maps.persistence.repository.RepositoryMaritimeArea;
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
public class ServiceMaritimeArea {

    private final RepositoryMaritimeArea repositoryMaritimeArea;

    public DTOResponseMaritimeArea create(DTORequestMaritimeArea created){
        return MapStruct.MAPPER.toDTO(repositoryMaritimeArea.save(MapStruct.MAPPER.toObject(created)));
    }
    public Page<DTOResponseMaritimeArea> retrieve(Pageable pageable, String value) {
        MaritimeArea object = new MaritimeArea();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<MaritimeArea> example = Example.of(object, exampleMatcher);
            return repositoryMaritimeArea.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryMaritimeArea.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryMaritimeArea.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    public DTOResponseMaritimeArea update(UUID id, DTORequestMaritimeArea updated){
        return MapStruct.MAPPER.toDTO(repositoryMaritimeArea.save(MapStruct.MAPPER.toObject(updated)));
    }
    public DTOResponseMaritimeArea delete(UUID id){
        DTOResponseMaritimeArea dtoResponseMaritimeArea = MapStruct.MAPPER.toDTO(repositoryMaritimeArea.findById(id).orElse(null));
        repositoryMaritimeArea.deleteById(id);
        return dtoResponseMaritimeArea;
    }
    public void delete() {
        repositoryMaritimeArea.deleteAll();
    }

    public boolean existsByName(String value) {
        return repositoryMaritimeArea.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        return repositoryMaritimeArea.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}