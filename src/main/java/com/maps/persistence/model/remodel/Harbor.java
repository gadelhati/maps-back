package com.maps.persistence.model.remodel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Harbor extends GeoEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String chartNumber;
    private Integer reference;

    @OneToMany(mappedBy = "harborArrived", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Commission> commissionsArrived = new HashSet<>();
    @OneToMany(mappedBy = "harborDeparture", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Commission> commissionsDeparture = new HashSet<>();
}