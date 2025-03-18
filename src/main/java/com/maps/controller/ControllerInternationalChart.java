package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestInternationalChart;
import com.maps.persistence.payload.response.DTOResponseInternationalChart;
import com.maps.service.ServiceInternationalChart;
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

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController @RequestMapping("/internationalChart") @RequiredArgsConstructor
public class ControllerInternationalChart {

    private final ServiceInternationalChart serviceInternationalChart;

    @PostMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseInternationalChart> create(@RequestBody @Valid DTORequestInternationalChart created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/international_chart").toUriString());
        return ResponseEntity.created(uri).body(serviceInternationalChart.create(created));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseInternationalChart>> retrieve(@RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceInternationalChart.retrieve(pageable, value));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseInternationalChart> update(@RequestBody @Valid DTORequestInternationalChart updated){
        return ResponseEntity.accepted().body(serviceInternationalChart.update(updated.getId(), updated));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseInternationalChart> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceInternationalChart.delete(id));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            serviceInternationalChart.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}