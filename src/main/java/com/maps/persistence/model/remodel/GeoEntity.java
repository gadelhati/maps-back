package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.awt.*;

@MappedSuperclass
public class GeoEntity extends GenericAuditEntity {

    @Column(columnDefinition = "geography")
    private Point point;
}