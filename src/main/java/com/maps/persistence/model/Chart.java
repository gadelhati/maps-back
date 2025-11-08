package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Chart extends GenericAuditEntity {

    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private String category;//[ COASTAL | OCEANIC ] | [ ACCESS | PORTS | TERMINALS ]
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point northEastPoint;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point southWestPoint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chart_area_id")
    private ChartArea chartArea;
}
