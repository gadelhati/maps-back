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
public class GaugeStation extends GenericAuditEntity {

    private String number;
    private String title;
    @Column(columnDefinition = "geography")
    private Point point;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private State state;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private ChartArea chartArea;
}