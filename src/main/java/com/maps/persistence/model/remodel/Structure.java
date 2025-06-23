package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "structures")
public class Structure extends NavigationAid {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private Double length;// Comprimento
    private Double beam;// Boca (largura)
    private Double draft;// Calado
    private Float swingingCircle;
    private Float altitude;
    private Float height;
    private String buildingMaterial;
    private String telegraphCallSign;
    private String internationalCallSign;
    private String visualCallSign;
    private String number;
    private boolean radarReflector;
    private boolean conspicuous;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "structure_format_id", nullable = false)
    private StructureFormat structureFormat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
}