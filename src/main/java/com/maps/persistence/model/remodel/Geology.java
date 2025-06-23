package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@Table(name = "geologies")
@PrimaryKeyJoinColumn(name="id")
public class Geology extends Media {

//    private Integer areaOrigin;
    private Integer dragVelocity;
    private Integer dragDirection;
    private Integer dragDuration;
    private Boolean photoSample;
    private Boolean photoFloor;
    private String sideScan;//null, N, S, O
    private Integer sampleCategory;//?
    private Float sampleSize;
    private Float sampleWeight;
    private String sampleColor;
    private String sampleTexture;
    private Boolean concretions;
    private Boolean shells;
    private Boolean plants;
    private Boolean animals;
    private String pollution;//null, N, O, 0, 1, 2, 3, 4
    private Integer tenseMain;
    private Integer tenseComplementary;
    private String tenseSample;//null, 0, 1, 2, 3
    @Column(length = 300)
    private String preliminaryAnalysis;
    private String analysis;
    private Integer survey;
    private Integer structure;
    private String sampleSequence;
    private Integer lengthMark;
    private Integer colorBottom;
    private Integer colorTop;
    private Integer numnac;//?
    private Integer qualityControl;
    private Integer DegreeSelection;
    private Integer DegreeRounding;
}