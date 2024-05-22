package br.eti.gadelha.maps.service;

import br.eti.gadelha.maps.persistence.MapStruct;
import br.eti.gadelha.maps.persistence.model.Chart;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestChart;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseChart;
import br.eti.gadelha.maps.persistence.repository.RepositoryChart;
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
public class ServiceChart implements ServiceInterface<DTOResponseChart, DTORequestChart> {

    private final RepositoryChart repositoryChart;

    @Override
    public DTOResponseChart create(DTORequestChart created){
        return MapStruct.MAPPER.toDTO(repositoryChart.save(MapStruct.MAPPER.toObject(created)));
    }
    @Override
    public Page<DTOResponseChart> retrieve(Pageable pageable, String value) {
        Chart object = new Chart();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            if(Objects.equals(UUID.fromString(value).toString(), value)) {
                return repositoryChart.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
            }
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<Chart> example = Example.of(object, exampleMatcher);
            return repositoryChart.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e){
            return repositoryChart.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseChart update(UUID id, DTORequestChart updated){
        return MapStruct.MAPPER.toDTO(repositoryChart.save(MapStruct.MAPPER.toObject(updated)));
    }
    @Override
    public DTOResponseChart delete(UUID id){
        DTOResponseChart dtoResponseChart = MapStruct.MAPPER.toDTO(repositoryChart.findById(id).orElse(null));
        repositoryChart.deleteById(id);
        return dtoResponseChart;
    }
    @Override
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