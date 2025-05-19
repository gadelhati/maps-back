package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.remodel.GeoEntity;
import com.maps.persistence.model.remodel.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Seismic extends Media {

    private String nameSeismicLine;
    private LocalDateTime startSeismicLineData;
    private LocalDateTime endSeismicLineData;
    private String qualityControl;
}