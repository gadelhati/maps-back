package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.remodel.Media;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@DiscriminatorValue("GEOLOGY")
public class GeologicalSample extends Media {

    private Integer codGeologicalSample;
    private Integer codStation;
    private Integer areaOrigin;
    private Integer dragVelocity;
    private Integer dragDirection;
    private Integer dragDuration;
    private Boolean photoSample;
    private Boolean photoFloor;
    private String sideScan;//null, N, S, O
    private Integer sampleType;
    private Float sampleVolume;
    private Float sampleWeight;
    private String sampleColor;
    private String sampleTexture;
    private Boolean concretions;
    private Boolean shells;
    private Boolean plants;
    private Boolean animals;
    private String pollution;//null, N, O, 0, 1, 2, 3, 4
    private Integer principalSeabed;
    private Integer complementarySeabed;
    private String sampleSeabed;//null, 0, 1, 2, 3
    @Column(length = 300)
    private String preliminaryAnalysis;
    private String identification;
    private Integer sondagem;
    private Integer structure;
    private String sampleSequence;
    private Integer comprimentoTestemunho;
    private Integer bottomColor;
    private Integer topColor;
    private Integer numnac;
    private Integer qualityControl;
    private Integer selectionDegree;
    private Integer roundingDegree;
}