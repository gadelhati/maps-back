package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cruiseLegs")
public class CruiseLeg extends GenericAuditEntity {

    private LocalDateTime start;
    private LocalDateTime finish;

    @OneToMany(mappedBy = "cruiseLeg", orphanRemoval = true)
    private Set<EquipmentDeployment> equipmentDeployments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cruise_id", nullable = false)
    private Cruise cruise;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "port_arrived_id")
    private Port portArrived;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "port_departure_id")
    private Port portDeparture;

    public Set<EquipmentDeployment> getEquipmentDeployments() {
        return Collections.unmodifiableSet(equipmentDeployments);
    }
    public void addEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.add(deployment);
        deployment.setCruiseLeg(this);
    }
    public void removeEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.remove(deployment);
        deployment.setCruiseLeg(null);
    }
}