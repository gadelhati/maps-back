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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Module extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "module", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Research> researches = new HashSet<>();
//    @OneToMany(mappedBy = "module", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private Set<EquipmentCategory> equipmentCategories = new HashSet<>();
//    @OneToMany(mappedBy = "module", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private Set<Equipment> equipments = new HashSet<>();
}