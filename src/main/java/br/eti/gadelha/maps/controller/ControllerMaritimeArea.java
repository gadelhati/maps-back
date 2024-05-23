package br.eti.gadelha.maps.controller;

import br.eti.gadelha.maps.persistence.payload.request.DTORequestMaritimeArea;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseMaritimeArea;
import br.eti.gadelha.maps.service.ServiceMaritimeArea;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController @RequestMapping("/maritimeArea") @RequiredArgsConstructor
public class ControllerMaritimeArea implements ControllerInterface<DTOResponseMaritimeArea, DTORequestMaritimeArea> {

    private final ServiceMaritimeArea serviceMaritimeArea;

    @PostMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseMaritimeArea> create(@RequestBody @Valid DTORequestMaritimeArea created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/maritime_area").toUriString());
        return ResponseEntity.created(uri).body(serviceMaritimeArea.create(created));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseMaritimeArea>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceMaritimeArea.retrieve(pageable, value));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseMaritimeArea> update(@RequestBody @Valid DTORequestMaritimeArea updated){
        return ResponseEntity.accepted().body(serviceMaritimeArea.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseMaritimeArea> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceMaritimeArea.delete(id));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            serviceMaritimeArea.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}