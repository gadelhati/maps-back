package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "media_type")
public class Media extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String description;
    private String path;
    private LocalDateTime generatedAt;
    private LocalDateTime deliveryAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private MediaCategory mediaCategory;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private EquipmentDeployment equipmentDeployment;
}