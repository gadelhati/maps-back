package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Equipment extends GenericAuditEntity {

    private int code;
    private String number;
    private String model;
    private Integer frequency;
    private Integer range;
    private LocalDateTime calibration;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "equipmentCategory", nullable = true)
    private EquipmentCategory equipmentCategory;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manufacturer", nullable = true)
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sampleMethod", nullable = true)
    private SampleMethod sampleMethod;
}