package com.maps.service;

import com.maps.MapsApplication;
import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.payload.request.Identifiable;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
@RequiredArgsConstructor
public abstract class ServiceGeneric<T extends GenericAuditEntity, DTORequest extends Identifiable, DTOResponse extends RepresentationModel<DTOResponse>> implements ServiceInterface<T, DTORequest, DTOResponse> {

    private final Information information;
    private final RepositoryGeneric<T> repositoryGeneric;
    private final MapperInterface<T, DTORequest, DTOResponse> mapperInterface;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    @Transactional
    public DTOResponse create(DTORequest created){
        LOGGER.info("{} creating a new user: {}", information.getCurrentUser(), created);
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(created)));
    }
    @Transactional
    public Page<DTOResponse> retrieve(Pageable pageable, String value, Class<T> entityClass) {
        try {
            T object = entityClass.getDeclaredConstructor().newInstance();
            ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            String propertyName = pageable.getSort().stream().findFirst()
                    .map(Sort.Order::getProperty)
                    .orElse("Id");
            Field field = ReflectionUtils.findField(entityClass, propertyName);
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(propertyName), field.getType());
            Object convertedValue = ConvertUtils.convert(value, field.getType());
            setMethod.invoke(object, convertedValue);
            Example<T> example = Example.of(object, exampleMatcher);
            return repositoryGeneric.findAll(example, pageable).map(element-> addHateoas(element, entityClass));
        } catch (IllegalArgumentException exception) {
            LOGGER.warn("No setter method found for property.");
            return repositoryGeneric.findById(pageable, UUID.fromString(value)).map(element-> addHateoas(element, entityClass));
        } catch (Exception exception) {
            return repositoryGeneric.findAll(pageable).map(element-> addHateoas(element, entityClass));
        }
    }
    public DTOResponse addHateoas(T object, Class<T> entityClass) {
        return mapperInterface.toDTO(object).add(
                linkTo(ServiceUser.class)
                        .slash(entityClass.getSimpleName().substring(0, 1).toLowerCase() + entityClass.getSimpleName().substring(1))
                        .slash(object.getId()).withSelfRel());
    }
    @Transactional
    public Optional<DTOResponse> retrieve(UUID id, Class<T> entityClass){
        repositoryGeneric.findById(id);
        T entity = repositoryGeneric.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        return Optional.of(mapperInterface.toDTO(entity).add(linkTo(ServiceUser.class).slash(entityClass.getSimpleName().toLowerCase()).slash(entity.getId()).withSelfRel()));
    }
    @Transactional
    public DTOResponse update(DTORequest updated){
        if (!repositoryGeneric.existsById(updated.getId())) {
            throw new RuntimeException("ID " + updated.getId() + " not found");
        }
        LOGGER.info("{} updating entity with ID: {}", information.getCurrentUser(), updated.getId());
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(updated)));
    }
    @Transactional
    public DTOResponse delete(UUID id){
        T entity = repositoryGeneric.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        LOGGER.info("{} deleting entity with ID: {}", information.getCurrentUser(), id);
        repositoryGeneric.deleteById(id);
        return mapperInterface.toDTO(entity);
    }
//    @Transactional
//    public void delete() {
//        LOGGER.info("Deleting all entities");
//        repositoryGeneric.deleteAll();
//    }
}