package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marsdenSquares", indexes = {@Index(columnList = "number"), @Index(columnList = "northEastPoint, southWestPoint")}, uniqueConstraints = @UniqueConstraint(columnNames = {"number"}))
public class MarsdenSquare extends GenericAuditEntity {

    private String number;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point northEastPoint;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point southWestPoint;
}
