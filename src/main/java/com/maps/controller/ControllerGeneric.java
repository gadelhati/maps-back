package com.maps.controller;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.payload.request.Identifiable;
import com.maps.service.ServiceGeneric;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RequiredArgsConstructor
public abstract class ControllerGeneric<T extends GenericAuditEntity, I extends Identifiable, O extends RepresentationModel<O>> implements ControllerInterface<I, O> {

    private final ServiceGeneric<T, I, O> serviceInterface;

    protected abstract Class<T> getEntityClass();
//    @RateLimit(requests = 100, period = "1m")
//    @RequiresPermission(permissions = {EnumPermission.CREATE}, roles = {"ROLE_ADMIN"}, type = EnumRequirementType.ALL)
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:create')")
    public ResponseEntity<O> create(@RequestBody @Valid I created){
        String localPath = this.getClass().isAnnotationPresent(RequestMapping.class) ? this.getClass().getAnnotation(RequestMapping.class).value()[0] : "";
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(localPath).toUriString());
        return ResponseEntity.created(uri).body(serviceInterface.create(created));
    }
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER') and hasAnyAuthority('user:retrieve')")
    public ResponseEntity<Page<O>> retrieve(@RequestParam(name = "value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceInterface.retrieve(pageable, value, getEntityClass()));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER') and hasAnyAuthority('user:retrieve')")
    public ResponseEntity<Optional<O>> retrieve(@PathVariable UUID id){
        return ResponseEntity.ok().body(serviceInterface.retrieve(id, getEntityClass()));
    }
    @PutMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') and hasAnyAuthority('user:update')")
    public ResponseEntity<O> update(@RequestBody @Valid I updated){
        return ResponseEntity.accepted().body(serviceInterface.update(updated));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    public ResponseEntity<O> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceInterface.delete(id));
    }
//    @DeleteMapping("")
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
//    public ResponseEntity<HttpStatus> delete(){
//        try {
//            serviceInterface.deleteAll();
//            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
//        }
//    }
}