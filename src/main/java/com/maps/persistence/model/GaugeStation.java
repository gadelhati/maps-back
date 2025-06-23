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
public class GaugeStation extends GenericAuditEntity {

    private String number;
    private String title;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    private State state;
    @ManyToOne(fetch = FetchType.LAZY)
    private ChartArea chartArea;
}