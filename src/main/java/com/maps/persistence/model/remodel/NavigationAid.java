package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.awt.*;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "navigationAids", indexes = {@Index(columnList = "point")})
@Inheritance(strategy = InheritanceType.JOINED)
public class NavigationAid extends GenericAuditEntity {

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point point;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "light_characteristic_id")
    private LightCharacteristic lightCharacteristic; // Nullable for blind signals
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintainer_id")
    private Maintainer maintainer;
}