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

import java.awt.*;
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
@Table(name = "ports", indexes = {@Index(columnList = "point")})
public class Port extends GenericAuditEntity {

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point point;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String chartNumber;
    private Integer reference;

    @OneToMany(mappedBy = "portArrived", orphanRemoval = true)
    private Set<CruiseLeg> portsArrived = new HashSet<>();
    @OneToMany(mappedBy = "portDeparture", orphanRemoval = true)
    private Set<CruiseLeg> portsDeparture = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public Set<CruiseLeg> getArriveds() {
        return Collections.unmodifiableSet(portsArrived);
    }
    public void addArrived(CruiseLeg cruiseLeg) {
        portsArrived.add(cruiseLeg);
        cruiseLeg.setPortArrived(this);
    }
    public void removeArrived(CruiseLeg cruiseLeg) {
        portsArrived.remove(cruiseLeg);
        cruiseLeg.setPortArrived(null);
    }

    public Set<CruiseLeg> getDepartures() {
        return Collections.unmodifiableSet(portsDeparture);
    }
    public void addDeparture(CruiseLeg cruiseLeg) {
        portsDeparture.add(cruiseLeg);
        cruiseLeg.setPortDeparture(this);
    }
    public void removeDeparture(CruiseLeg cruiseLeg) {
        portsDeparture.remove(cruiseLeg);
        cruiseLeg.setPortDeparture(null);
    }
}