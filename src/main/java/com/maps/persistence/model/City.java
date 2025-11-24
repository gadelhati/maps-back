package com.maps.persistence.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities", indexes = {@Index(columnList = "name, state_id")}, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "state_id"}))
public class City extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "city", orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }
    public void addAddress(Address address) {
        addresses.add(address);
        address.setCity(this);
    }
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setCity(null);
    }
}
