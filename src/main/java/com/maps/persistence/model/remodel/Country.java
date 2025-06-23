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
@Table(name = "countries", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Country extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    private Set<State> states = new HashSet<>();
    @OneToMany(mappedBy = "country", orphanRemoval = true)
    private Set<Institution> institutions = new HashSet<>();

    public Set<State> getStates() {
        return Collections.unmodifiableSet(states);
    }
    public void addState(State state) {
        states.add(state);
        state.setCountry(this);
    }
    public void removeState(State state) {
        states.remove(state);
        state.setCountry(null);
    }
}