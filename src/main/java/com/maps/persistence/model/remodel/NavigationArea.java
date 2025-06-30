package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
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
@Table(name = "navigationArea", indexes = {@Index(columnList = "name, description")}, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "description"}))
public class NavigationArea extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String description;

    @OneToMany(mappedBy = "navigationArea", orphanRemoval = true)
    private Set<Vessel> vessels = new HashSet<>();

    public Set<Vessel> getVessels() {
        return Collections.unmodifiableSet(vessels);
    }
    public void addVessel(Vessel vessel) {
        vessels.add(vessel);
        vessel.setNavigationArea(this);
    }
    public void removeVessel(Vessel vessel) {
        vessels.remove(vessel);
        vessel.setNavigationArea(null);
    }
}