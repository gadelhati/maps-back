package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
public class Equipment extends GenericAuditEntity {

    private String number;
    private String model;
    private Integer frequency;
    private Integer range;
    private LocalDateTime calibration;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private EquipmentCategory equipmentCategory;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private SampleMethod sampleMethod;
}