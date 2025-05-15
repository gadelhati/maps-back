package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.Research;
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
public class EquipmentDeployment extends GenericAuditEntity {

    private LocalDateTime deployedAt;
    private LocalDateTime retrievedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Research research;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Platform platform;
}