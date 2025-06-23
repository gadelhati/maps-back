package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
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
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "states", indexes = {@Index(columnList = "name, country_id")}, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country_id"}))
public class State extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "state", orphanRemoval = true)
    private Set<City> cities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Set<City> getCities() {
        return Collections.unmodifiableSet(cities);
    }
    public void addCity(City city) {
        cities.add(city);
        city.setState(this);
    }
    public void removeVessel(City city) {
        cities.remove(city);
        city.setState(null);
    }
}