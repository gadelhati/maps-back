package br.eti.gadelha.maps.controller;

import br.eti.gadelha.maps.persistence.payload.request.DTORequestGaugeStation;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseGaugeStation;
import br.eti.gadelha.maps.service.ServiceGaugeStation;
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

@RestController @RequestMapping("/gaugeStation") @RequiredArgsConstructor
public class ControllerGaugeStation implements ControllerInterface<DTOResponseGaugeStation, DTORequestGaugeStation> {

    private final ServiceGaugeStation serviceGaugeStation;

    @PostMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseGaugeStation> create(@RequestBody @Valid DTORequestGaugeStation created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/gauge_station").toUriString());
        return ResponseEntity.created(uri).body(serviceGaugeStation.create(created));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseGaugeStation>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceGaugeStation.retrieve(pageable, value));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseGaugeStation> update(@RequestBody @Valid DTORequestGaugeStation updated){
        return ResponseEntity.accepted().body(serviceGaugeStation.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseGaugeStation> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceGaugeStation.delete(id));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            serviceGaugeStation.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}