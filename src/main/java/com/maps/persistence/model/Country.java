package com.maps.persistence.model;

import com.maps.exception.annotation.UniqueNameCountry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

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
@Table(indexes = @Index(columnList = "name"), uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@UniqueNameCountry
public class Country extends GenericAuditEntity {

    private Integer code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
}