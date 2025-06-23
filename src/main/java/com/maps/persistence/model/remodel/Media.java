package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.awt.*;
import java.time.LocalDateTime;

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
@Table(name = "medias", indexes = {@Index(columnList = "point")})
@Inheritance(strategy = InheritanceType.JOINED)
public class Media extends GenericAuditEntity {

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point point;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String description;
    private String path;
    private Long length;
    private String checksum;
    private LocalDateTime generatedAt;
    private LocalDateTime deliveryAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "media_category_id", nullable = false)
    private MediaCategory mediaCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_deployment_id")
    private EquipmentDeployment equipmentDeployment;
}