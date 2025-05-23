package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
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

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "equipmentDeployments")
public class EquipmentDeployment extends GenericAuditEntity {

    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalDateTime deployedAt;
    private LocalDateTime retrievedAt;
    @Column(columnDefinition = "geography")
    private Point ne;//lat_topmost and long_rightmost
    @Column(columnDefinition = "geography")
    private Point sw;//lat_bottommost and long_leftmost
    private Integer equipmentDepth;
    private Integer localDepth;

    private String totalSizeMedia;
    private String dataQualification;
    private String hFolder;

    @OneToMany(mappedBy = "equipmentDeployment", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Media> medias = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Research research;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private CruiseLeg cruiseLeg;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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