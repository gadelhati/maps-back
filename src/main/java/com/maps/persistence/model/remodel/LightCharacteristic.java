package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lightCharacteristics")
public class LightCharacteristic extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String typeAbbreviation; // e.g., "Lp.", "R (2)"
    private String period; // e.g., "10s"
    private String detailedPhase; // e.g., "B. 0,5 – Ecl. 9,5"
    private Integer rangeLuminous;
    private Integer rangeGeographic;

    @OneToMany(mappedBy = "lightCharacteristic", orphanRemoval = true)
    private Set<LightColor> lightColors = new HashSet<>();
    @OneToMany(mappedBy = "lightCharacteristic", orphanRemoval = true)
    private Set<NavigationAid> navigationAids = new HashSet<>();

    public Set<LightColor> getLightColors() {
        return Collections.unmodifiableSet(lightColors);
    }
    public void addLightColor(LightColor lightColor) {
        lightColors.add(lightColor);
        lightColor.setLightCharacteristic(this);
    }
    public void removeLightColor(LightColor lightColor) {
        lightColors.remove(lightColor);
        lightColor.setLightCharacteristic(null);
    }

    public Set<NavigationAid> getNavigationAid() {
        return Collections.unmodifiableSet(navigationAids);
    }
    public void addNavigationAid(NavigationAid navigationAid) {
        navigationAids.add(navigationAid);
        navigationAid.setLightCharacteristic(this);
    }
    public void removeNavigationAid(NavigationAid navigationAid) {
        navigationAids.remove(navigationAid);
        navigationAid.setLightCharacteristic(null);
    }
}