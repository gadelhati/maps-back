package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.payload.request.DTORequestIdentifiable;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.data.domain.*;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class ServiceGeneric<T extends GenericAuditEntity, DTORequest extends DTORequestIdentifiable, DTOResponse extends RepresentationModel<DTOResponse>> implements ServiceInterface<T, DTORequest, DTOResponse> {

    private final Class<T> entityClass;
    private final Information information;
    private final RepositoryGeneric<T> repositoryGeneric;
    private final MapperInterface<T, DTORequest, DTOResponse> mapperInterface;
    
    @Transactional
    public DTOResponse create(DTORequest created){
        log.info("{} creating a new resource", information.getCurrentUser().orElse("Unknown User"));
        T entity = repositoryGeneric.save(mapperInterface.toObject(created));
        return addHateoas(entity);
    }
    @Transactional
    public Page<DTOResponse> retrieve(Pageable pageable, String value, Class<T> entityClass) {
        String propertyName = pageable.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .orElse("id");
        log.debug("Retrieving {} with property: {}, value: {}", entityClass.getSimpleName(), propertyName, value);
        if ("id".equalsIgnoreCase(propertyName) && StringUtils.hasText(value)) {
            try {
                return repositoryGeneric.findById(pageable, UUID.fromString(value))
                        .map(this::addHateoas);
            } catch (IllegalArgumentException e){
                log.debug("Value '{}' is not a valid UUID, falling back to property search", value);
            }
        }
        try {
            T object = entityClass.getDeclaredConstructor().newInstance();
            ExampleMatcher exampleMatcher = matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Field field = ReflectionUtils.findField(entityClass, propertyName);
            String setterName = "set" + StringUtils.capitalize(propertyName);
            Method setter = object.getClass().getDeclaredMethod(setterName, field.getType());
            Object convertedValue = ConvertUtils.convert(value, field.getType());
            setter.invoke(object, convertedValue);
            Example<T> example = Example.of(object, exampleMatcher);
            return repositoryGeneric.findAll(example, pageable).map(this::addHateoas);
        } catch (Exception exception) {
            log.warn("Error searching {} by {}: {}", entityClass.getSimpleName(), propertyName, exception.getMessage());
            return repositoryGeneric.findAll(pageable).map(this::addHateoas);
        }
    }
    @Transactional
    public DTOResponse retrieve(UUID id){
        log.debug("Retrieving {} with ID: {}", entityClass.getSimpleName(), id);
        T entity = repositoryGeneric.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s not found with ID: %s", entityClass.getSimpleName(), id)));
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse update(UUID id, DTORequest updated){
        log.info("{} updating entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), updated.id());
        T entity = repositoryGeneric.save(mapperInterface.toObject(updated));
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse delete(UUID id){
        log.info("{} deleting entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
        T entity = repositoryGeneric.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot delete: %s not found with ID: %s", entityClass.getSimpleName(), id)));
        repositoryGeneric.delete(entity);
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse addHateoas(T object) {
        String entityName = Character.toLowerCase(entityClass.getSimpleName().charAt(0))
                + entityClass.getSimpleName().substring(1);

        String selfUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(entityName, String.valueOf(object.getId()))
                .toUriString();

        return mapperInterface.toDTO(object).add(Link.of(selfUri, IanaLinkRelations.SELF));
    }
}
