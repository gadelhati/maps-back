package com.maps.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "datums")
public class Datum extends GenericAuditEntity {

    private String name;
    private Integer ae;
    private Float umF;

    @OneToMany(mappedBy = "datum")
    private Set<EquipmentDeployment> equipmentsDeployment = new HashSet<>();

    public Set<EquipmentDeployment> getEquipmentDeployment() {
        return Collections.unmodifiableSet(equipmentsDeployment);
    }
    public void addEquipmentDeployment(EquipmentDeployment equipmentDeployment) {
        equipmentsDeployment.add(equipmentDeployment);
        equipmentDeployment.setDatum(this);
    }
    public void removeEquipmentDeployment(EquipmentDeployment equipmentDeployment) {
        equipmentsDeployment.remove(equipmentDeployment);
        equipmentDeployment.setDatum(null);
    }
}
