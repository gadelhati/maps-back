package br.eti.gadelha.maps.service;

import br.eti.gadelha.maps.persistence.MapStruct;
import br.eti.gadelha.maps.persistence.model.MaritimeArea;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestMaritimeArea;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseMaritimeArea;
import br.eti.gadelha.maps.persistence.repository.RepositoryMaritimeArea;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public class ServiceMaritimeArea implements ServiceInterface<DTOResponseMaritimeArea, DTORequestMaritimeArea> {

    private final RepositoryMaritimeArea repositoryMaritimeArea;

    @Override
    public DTOResponseMaritimeArea create(DTORequestMaritimeArea created){
        return MapStruct.MAPPER.toDTO(repositoryMaritimeArea.save(MapStruct.MAPPER.toObject(created)));
    }
    @Override
    public Page<DTOResponseMaritimeArea> retrieve(Pageable pageable, String value) {
        MaritimeArea object = new MaritimeArea();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<MaritimeArea> example = Example.of(object, exampleMatcher);
            return repositoryMaritimeArea.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e){
            if(Objects.equals(UUID.fromString(value).toString(), value)) {
                return repositoryMaritimeArea.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
            }
            return repositoryMaritimeArea.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseMaritimeArea update(UUID id, DTORequestMaritimeArea updated){
        return MapStruct.MAPPER.toDTO(repositoryMaritimeArea.save(MapStruct.MAPPER.toObject(updated)));
    }
    @Override
    public DTOResponseMaritimeArea delete(UUID id){
        DTOResponseMaritimeArea dtoResponseMaritimeArea = MapStruct.MAPPER.toDTO(repositoryMaritimeArea.findById(id).orElse(null));
        repositoryMaritimeArea.deleteById(id);
        return dtoResponseMaritimeArea;
    }
    @Override
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