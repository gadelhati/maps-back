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

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "lightCharacteristics")
public class LightCharacteristic extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String typeAbbreviation; // e.g., "Lp.", "R (2)"
    private String period; // e.g., "10s"
    private String detailedPhase; // e.g., "B. 0,5 – Ecl. 9,5"
    private Integer rangeLuminous;
    private Integer rangeGeographic;
    @Enumerated(EnumType.STRING)
    private LightColor color;

    @OneToOne(mappedBy = "lightCharacteristic")
    private NavigationAid navigationAid;
}