package com.maps.persistence.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Researcher extends GenericAuditEntity {

    private String code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String email;
    private String address;
}