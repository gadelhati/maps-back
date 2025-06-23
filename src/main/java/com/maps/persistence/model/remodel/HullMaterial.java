package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "hullMaterial", uniqueConstraints = @UniqueConstraint(columnNames = {"description"}))
public class HullMaterial extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String description;

    @OneToMany(mappedBy = "hullMaterial", orphanRemoval = true)
    private Set<Vessel> vessels = new HashSet<>();

    public Set<Vessel> getVessels() {
        return Collections.unmodifiableSet(vessels);
    }
    public void addVessel(Vessel vessel) {
        vessels.add(vessel);
        vessel.setHullMaterial(this);
    }
    public void removeVessel(Vessel vessel) {
        vessels.remove(vessel);
        vessel.setHullMaterial(null);
    }
}