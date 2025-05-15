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
public class Address extends GenericAuditEntity {

    private String street;
    private String number;
    private String cep;
    private String complement;
    private String neighbourhood;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private City city;
}