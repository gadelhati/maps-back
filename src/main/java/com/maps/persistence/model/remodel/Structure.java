package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
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
@Inheritance(strategy = InheritanceType.JOINED)
public class Structure extends GenericAuditEntity {

    private float altitude;
    private float height;
    private float draft;
    private float swingingCircle;
    private String color;
    private String buildingMaterial;
    private String name;
    private String number;
    private boolean placaDeVisibilidade;//sem tradução conhecida
    private boolean reflectorRadar;
    private boolean conspicuous;
    private String telegraphCallSign;
    private String internationalCallSign;
    private String visualCallSign;
    private EnumFormat format;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cruise> cruises = new HashSet<>();

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private Radar radar;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Country country;
}