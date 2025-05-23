package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "cruiseLegs")
public class CruiseLeg extends GenericAuditEntity {

    private LocalDateTime start;
    private LocalDateTime finish;

    @OneToMany(mappedBy = "cruiseLeg", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EquipmentDeployment> equipmentDeployments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Cruise cruise;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Port portArrived;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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