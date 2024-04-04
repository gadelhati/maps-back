package br.eti.gadelha.maps.service;

import br.eti.gadelha.maps.persistence.MapStruct;
import br.eti.gadelha.maps.persistence.model.GaugeStation;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestGaugeStation;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseGaugeStation;
import br.eti.gadelha.maps.persistence.repository.RepositoryGaugeStation;
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
public class ServiceGaugeStation implements ServiceInterface<DTOResponseGaugeStation, DTORequestGaugeStation> {

    private final RepositoryGaugeStation repositoryGaugeStation;

    @Override
    public DTOResponseGaugeStation create(DTORequestGaugeStation created){
        return MapStruct.MAPPER.toDTO(repositoryGaugeStation.save(MapStruct.MAPPER.toObject(created)));
    }
    @Override
    public Page<DTOResponseGaugeStation> retrieve(Pageable pageable, String value) {
        GaugeStation object = new GaugeStation();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<GaugeStation> example = Example.of(object, exampleMatcher);
            return repositoryGaugeStation.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e){
            return repositoryGaugeStation.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseGaugeStation update(UUID id, DTORequestGaugeStation updated){
        return MapStruct.MAPPER.toDTO(repositoryGaugeStation.save(MapStruct.MAPPER.toObject(updated)));
    }
    @Override
    public DTOResponseGaugeStation delete(UUID id){
        DTOResponseGaugeStation dtoResponseGaugeStation = MapStruct.MAPPER.toDTO(repositoryGaugeStation.findById(id).orElse(null));
        repositoryGaugeStation.deleteById(id);
        return dtoResponseGaugeStation;
    }
    @Override
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