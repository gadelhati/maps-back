package br.eti.gadelha.maps.controller;

import br.eti.gadelha.maps.persistence.payload.request.DTORequestChartArea;
import br.eti.gadelha.maps.persistence.payload.response.DTOResponseChartArea;
import br.eti.gadelha.maps.service.ServiceChartArea;
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

@RestController @RequestMapping("/chart_area") @RequiredArgsConstructor
public class ControllerChartArea implements ControllerInterface<DTOResponseChartArea, DTORequestChartArea> {

    private final ServiceChartArea serviceChartArea;

    @PostMapping("") @PreAuthorize("hasAnyRole('52c57a80-4e3b-4a41-a864-58d0cea25b14', '8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponseChartArea> create(@RequestBody @Valid DTORequestChartArea created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/chart_area").toUriString());
        return ResponseEntity.created(uri).body(serviceChartArea.create(created));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('USER', '52c57a80-4e3b-4a41-a864-58d0cea25b14', '8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<Page<DTOResponseChartArea>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceChartArea.retrieve(pageable, value));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('52c57a80-4e3b-4a41-a864-58d0cea25b14', '8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponseChartArea> update(@RequestBody @Valid DTORequestChartArea updated){
        return ResponseEntity.accepted().body(serviceChartArea.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('52c57a80-4e3b-4a41-a864-58d0cea25b14', '8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<DTOResponseChartArea> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceChartArea.delete(id));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('52c57a80-4e3b-4a41-a864-58d0cea25b14', '8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            serviceChartArea.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}