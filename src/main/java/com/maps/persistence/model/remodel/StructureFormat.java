package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "structuresFormat")
public class StructureFormat extends GenericAuditEntity {

    private String name;

    @OneToMany(mappedBy = "structureFormat", orphanRemoval = true)
    private Set<Structure> structures = new HashSet<>();

    public Set<Structure> getStructures() {
        return Collections.unmodifiableSet(structures);
    }
    public void addStructure(Structure structure) {
        structures.add(structure);
        structure.setStructureFormat(this);
    }
    public void removeStructure(Structure structure) {
        structures.remove(structure);
        structure.setStructureFormat(null);
    }
}