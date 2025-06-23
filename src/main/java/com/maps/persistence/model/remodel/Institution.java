package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
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
@Table(name = "institutions")
public class Institution extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private boolean brazilianNavy;

    @OneToMany(mappedBy = "coordinator", orphanRemoval = true)
    private Set<Cruise> coordinators = new HashSet<>();
    @OneToMany(mappedBy = "responsible", orphanRemoval = true)
    private Set<Cruise> responsible = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    public Set<Cruise> getCoordinators() {
        return Collections.unmodifiableSet(coordinators);
    }
    public void addCoordinator(Cruise cruise) {
        coordinators.add(cruise);
        cruise.setCoordinator(this);
    }
    public void removeCoordinator(Cruise cruise) {
        coordinators.remove(cruise);
        cruise.setCoordinator(null);
    }

    public Set<Cruise> getResponsible() {
        return Collections.unmodifiableSet(responsible);
    }
    public void addResponsible(Cruise cruise) {
        responsible.add(cruise);
        cruise.setResponsible(this);
    }
    public void removeResponsible(Cruise cruise) {
        responsible.remove(cruise);
        cruise.setResponsible(null);
    }
}
