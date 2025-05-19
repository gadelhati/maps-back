package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Cruise extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String number;
    private String description;
    @Column(columnDefinition = "geography")
    private Point sw;//latBottomMost and longLeftMost
    @Column(columnDefinition = "geography")
    private Point ne;//latTopMost and longRightMost
    private String areaName;
    private String maximumDepthArea;
    private String minimumDepthArea;
    private String maximumCollectionDepth;
    private String minimumCollectionDepth;
    private String totalSizeMedia;
    private String dataQualification;
    private String hFolder;

    @OneToMany(mappedBy = "cruise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CruiseLeg> cruiseLegs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Institution coordinator;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Institution responsible;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Structure structure;
}