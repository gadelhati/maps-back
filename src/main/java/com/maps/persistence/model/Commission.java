package com.maps.persistence.model;

import com.maps.persistence.model.bndo.Harbor;
import com.maps.persistence.model.bndo.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

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
public class Commission extends GenericAuditEntity {

    private String cruiseName;
    private String cruiseNumber;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") 
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Institution coordinator;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Institution responsible;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Harbor harborArrived;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Harbor harborDeparture;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Platform platform;
}