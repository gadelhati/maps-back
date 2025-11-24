package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

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
@Table(name = "lightColor")
public class LightColor extends GenericAuditEntity {

    private String name;
    private String abbreviation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "light_characteristic_id")
    private LightCharacteristic lightCharacteristic;
}
