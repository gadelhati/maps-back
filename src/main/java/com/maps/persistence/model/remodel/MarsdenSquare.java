package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MarsdenSquare extends GenericAuditEntity {

    public String number;
    @Column(columnDefinition = "geography")
    private Point ne;//lat_topmost and long_rightmost
    @Column(columnDefinition = "geography")
    private Point sw;//lat_bottommost and long_leftmost
}