package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
@Audited
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "structures")
public class Structure extends GeoEntity {

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
    @Enumerated(EnumType.STRING)
    private StructureFormat format;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Country country;
}