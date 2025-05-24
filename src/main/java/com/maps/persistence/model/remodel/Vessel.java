package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "vessels")
public class Vessel extends Structure {

    @Enumerated(EnumType.STRING)
    private VesselType type;
    @Enumerated(EnumType.STRING)
    private HullMaterial hullMaterial;
    @Enumerated(EnumType.STRING)
    private PropulsionSystem propulsionSystem;
    @Enumerated(EnumType.STRING)
    private NavigationArea navigationArea;

    @OneToMany(mappedBy = "vessel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cruise> cruises = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Radar radar;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Country country;

    public Set<Cruise> getCruises() {
        return Collections.unmodifiableSet(cruises);
    }

    public void addCruise(Cruise cruise) {
        cruises.add(cruise);
        cruise.setVessel(this);
    }

    public void removeCruise(Cruise cruise) {
        cruises.remove(cruise);
        cruise.setVessel(null);
    }
}