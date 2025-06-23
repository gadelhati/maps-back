package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "equipmentDeployments", indexes = {@Index(columnList = "northEastPoint, southWestPoint")})
public class EquipmentDeployment extends GenericAuditEntity {

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point northEastPoint;
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point southWestPoint;
    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalDateTime deployedAt;
    private LocalDateTime retrievedAt;
    private Integer equipmentDepth;
    private Integer localDepth;

    private String totalSizeMedia;
    private String dataQualification;
    private String hFolder;

    @OneToMany(mappedBy = "equipmentDeployment", orphanRemoval = true)
    private Set<Media> medias = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_id")
    private Research research;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cruise_leg_id")
    private CruiseLeg cruiseLeg;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "datum_id", nullable = false)
    private Datum datum;

    public Set<Media> getMedias() {
        return Collections.unmodifiableSet(medias);
    }
    public void addMedia(Media media) {
        medias.add(media);
        media.setEquipmentDeployment(this);
    }
    public void removeMedia(Media media) {
        medias.remove(media);
        media.setEquipmentDeployment(null);
    }
}