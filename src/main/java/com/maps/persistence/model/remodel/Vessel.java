package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
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
@Table(name = "vessels")
public class Vessel extends Structure {

    @OneToOne(mappedBy= "vessel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Radar radar;

    @OneToMany(mappedBy = "vessel", orphanRemoval = true)
    private Set<Cruise> cruises = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vessel_type_id", nullable = false)
    private VesselType vesselType;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "hull_material_id", nullable = false)
    private HullMaterial hullMaterial;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "propulsion_system_id", nullable = false)
    private PropulsionSystem propulsionSystem;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "navigation_area_id", nullable = false)
    private NavigationArea navigationArea;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
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