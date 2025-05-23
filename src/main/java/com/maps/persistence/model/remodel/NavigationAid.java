package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "navigationAid")
public class NavigationAid extends Structure {

    @NotBlank
    private String internationalNumber;
    @NotBlank
    private String orderNumber;
    private String lightRange;
    private String geographicRange;
    @NotBlank
    private String description;
    private String chartNumber;
    private String observations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private LightCharacteristic lightCharacteristic; // Nullable for blind signals
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Maintainer maintainer;
}