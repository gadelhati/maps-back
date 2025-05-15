package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
public class ResearchMember extends GenericAuditEntity {

    private String function;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Research research;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Researcher researcher;
}