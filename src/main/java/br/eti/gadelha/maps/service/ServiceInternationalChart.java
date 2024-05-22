package br.eti.gadelha.maps.service;

import br.eti.gadelha.maps.persistence.MapStruct;
import br.eti.gadelha.maps.persistence.model.InternationalChart;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestInternationalChart;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseInternationalChart;
import br.eti.gadelha.maps.persistence.repository.RepositoryInternationalChart;
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
public class ServiceInternationalChart implements ServiceInterface<DTOResponseInternationalChart, DTORequestInternationalChart> {

    private final RepositoryInternationalChart repositoryInternationalChart;

    @Override
    public DTOResponseInternationalChart create(DTORequestInternationalChart created){
        return MapStruct.MAPPER.toDTO(repositoryInternationalChart.save(MapStruct.MAPPER.toObject(created)));
    }
    @Override
    public Page<DTOResponseInternationalChart> retrieve(Pageable pageable, String value) {
        InternationalChart object = new InternationalChart();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            if(Objects.equals(UUID.fromString(value).toString(), value)) {
                return repositoryInternationalChart.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
            }
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<InternationalChart> example = Example.of(object, exampleMatcher);
            return repositoryInternationalChart.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e){
            return repositoryInternationalChart.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseInternationalChart update(UUID id, DTORequestInternationalChart updated){
        return MapStruct.MAPPER.toDTO(repositoryInternationalChart.save(MapStruct.MAPPER.toObject(updated)));
    }
    @Override
    public DTOResponseInternationalChart delete(UUID id){
        DTOResponseInternationalChart dtoResponseInternationalChart = MapStruct.MAPPER.toDTO(repositoryInternationalChart.findById(id).orElse(null));
        repositoryInternationalChart.deleteById(id);
        return dtoResponseInternationalChart;
    }
    @Override
    public void delete() {
        repositoryInternationalChart.deleteAll();
    }

    public boolean existsByNumber(String value) {
        return repositoryInternationalChart.existsByNumberIgnoreCase(value);
    }
    public boolean existsByNumberAndIdNot(String value, UUID id) {
        return repositoryInternationalChart.existsByNumberIgnoreCaseAndIdNot(value, id);
    }
}