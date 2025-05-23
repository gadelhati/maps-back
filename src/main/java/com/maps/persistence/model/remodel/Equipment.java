package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
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
@Table(name = "equipments", uniqueConstraints = @UniqueConstraint(columnNames = {"number"}))
public class Equipment extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String number;
    private String model;
    private Integer frequency;
    private Integer range;
    private LocalDateTime calibration;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<EquipmentDeployment> equipmentDeployments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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