package com.maps.persistence.model.sailingDirection;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@Inheritance(strategy = InheritanceType.JOINED)
public class Structure extends GenericAuditEntity {

    private float altitude;
    private float height;
    private float calado;//sem tradução conhecida
    private float circuloDeGiro;//sem tradução conhecida
    private String color;
    private String buildingMaterial;
    private String name;
    private String number;
    private boolean placaDeVisibilidade;//sem tradução conhecida
    private boolean refletorRadar;//sem tradução conhecida
    private boolean visivelmenteConspicuo;//sem tradução conhecida
    private String telegraphCallSign;
    private String internationalCallSign;
    private String visualCallSign;

    private EnumFormat format;
    private Radar radar;
}