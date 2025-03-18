package com.maps.controller;

import com.maps.persistence.model.CompositePK;
import com.maps.persistence.payload.request.DTORequestCompositeUnit;
import com.maps.persistence.payload.response.DTOResponseCompositeUnit;
import com.maps.service.ServiceCompositeUnit;
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

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/compositeUnit")
@RequiredArgsConstructor
public class ControllerCompositeUnit {

    private final ServiceCompositeUnit serviceCompositeUnit;

    @PostMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseCompositeUnit> create(@RequestBody @Valid DTORequestCompositeUnit created){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/composite_unit").toUriString());
        return ResponseEntity.created(uri).body(serviceCompositeUnit.create(created));
    }
    @GetMapping("/composite") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseCompositeUnit>> retrieve(
            Pageable pageable,
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "value", defaultValue = "", required = false) String value,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "number", required = false) int number){
        return ResponseEntity.ok().body(serviceCompositeUnit.retrieveComposite(pageable, key, value, name, number));
    }
    @GetMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<Page<DTOResponseCompositeUnit>> retrieve(@RequestParam(name = "key", defaultValue = "", required = false) String key, @RequestParam(name="value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceCompositeUnit.retrieve(pageable, key, value));
    }
    @PutMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseCompositeUnit> update(@RequestBody @Valid DTORequestCompositeUnit updated){
        return ResponseEntity.accepted().body(serviceCompositeUnit.update(new CompositePK(updated.getName(), updated.getNumber()), updated));
    }
    @DeleteMapping("/{name}/{number}") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681', '52c57a80-4e3b-4a41-a864-58d0cea25b14')")
    public ResponseEntity<DTOResponseCompositeUnit> delete(@PathVariable("name") String name, @PathVariable("number") int number){
        return ResponseEntity.accepted().body(serviceCompositeUnit.delete(new CompositePK(name, number)));
    }
    @DeleteMapping("") @PreAuthorize("hasAnyRole('8652ec73-0a53-433c-93be-420f1d90c681')")
    public ResponseEntity<HttpStatus> delete(){
        try {
            serviceCompositeUnit.delete();
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}