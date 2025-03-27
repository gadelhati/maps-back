package com.maps.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

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
public class MaritimeArea extends GenericAuditEntity {

    private String code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String start;
    private String finish;
    @Column(columnDefinition = "geography")
    private Polygon polygon;
    @Column(columnDefinition = "geography")
    private MultiPolygon multiPolygon;
}