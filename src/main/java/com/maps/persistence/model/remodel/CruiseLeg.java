package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CruiseLeg extends GenericAuditEntity {

    private LocalDateTime start;
    private LocalDateTime finish;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Cruise cruise;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Port portArrived;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Port portDeparture;
}
