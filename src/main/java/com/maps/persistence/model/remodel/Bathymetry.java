package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.awt.*;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Bathymetry extends Media {

    @Column(columnDefinition = "geography")
    private Point point;
    private Float depth;
    private String qualityControlPosition;
    private String qualityControlDepth;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Datum datum;
}