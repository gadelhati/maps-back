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

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String street;
    @NotNull(message = "{not.null}")
    private String number;
    private String cep;
    private String complement;
    private String neighbourhood;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private City city;
}