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
@Table(name = "cruises", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Cruise extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String number;
    private String description;
    private String areaName;
    private String maximumDepthArea;
    private String minimumDepthArea;
    private String maximumCollectionDepth;
    private String minimumCollectionDepth;

    @OneToMany(mappedBy = "cruise", orphanRemoval = true)
    private Set<CruiseLeg> cruiseLegs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id")
    private Institution coordinator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private Institution responsible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id")
    private Vessel vessel;

    public Set<CruiseLeg> getCruiseLegs() {
        return Collections.unmodifiableSet(cruiseLegs);
    }
    public void addCruiseLeg(CruiseLeg leg) {
        cruiseLegs.add(leg);
        leg.setCruise(this);
    }
    public void removeCruiseLeg(CruiseLeg leg) {
        cruiseLegs.remove(leg);
        leg.setCruise(null);
    }
}