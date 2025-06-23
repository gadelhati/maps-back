package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
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
@Table(name = "manufacturer", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Manufacturer extends GenericAuditEntity {

    private String name;

    @OneToMany(mappedBy = "manufacturer", orphanRemoval = true)
    private Set<Equipment> equipments = new HashSet<>();

    public Set<Equipment> getEquipments() {
        return Collections.unmodifiableSet(equipments);
    }
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setManufacturer(this);
    }
    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        equipment.setManufacturer(null);
    }
}