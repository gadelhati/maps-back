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

import java.time.LocalDate;
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
@Table(name = "equipments", uniqueConstraints = @UniqueConstraint(columnNames = {"number"}))
public class Equipment extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String number;
    private String model;
    private Integer frequency;
    private Integer range;
    private LocalDate calibration;

    @OneToMany(mappedBy = "equipment", orphanRemoval = true)
    private Set<EquipmentDeployment> equipmentDeployments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sample_method_id")
    private SampleMethod sampleMethod;

    public Set<EquipmentDeployment> getEquipmentDeployments() {
        return Collections.unmodifiableSet(equipmentDeployments);
    }
    public void addEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.add(deployment);
        deployment.setEquipment(this);
    }
    public void removeEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.remove(deployment);
        deployment.setEquipment(null);
    }
}