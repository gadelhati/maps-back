package com.maps.persistence.model;

import com.maps.persistence.model.remodel.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

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
public class BlindSignal extends GenericAuditEntity {

    private int number;
    private String name;
    private String chart;
    @Column(columnDefinition = "geography")
    private Point point;
    private String description;
    private String observation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private ChartArea chartArea;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private State state;
}