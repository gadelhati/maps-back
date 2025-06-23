package com.maps.persistence.model;

import com.maps.persistence.model.remodel.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

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
public class BlindSignal extends GenericAuditEntity {

    private int number;
    private String name;
    private String chart;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point point;
    private String description;
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_area_id")
    private ChartArea chartArea;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
}