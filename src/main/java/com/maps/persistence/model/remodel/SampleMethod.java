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
@Table(name = "sample_methods", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class SampleMethod extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "sampleMethod", orphanRemoval = true)
    private Set<Equipment> equipments = new HashSet<>();

    public Set<Equipment> getEquipments() {
        return Collections.unmodifiableSet(equipments);
    }
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setSampleMethod(this);
    }
    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        equipment.setSampleMethod(null);
    }
}