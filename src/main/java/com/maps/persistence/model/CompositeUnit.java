package com.maps.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.Date;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@IdClass(CompositePK.class)
@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class CompositeUnit {
    @Id
    private String name;
    @Id
    private int number;
    private String value;
    @Temporal(TemporalType.DATE)
    private Date date;
}
