package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@Table(name = "maintainers")
public class Maintainer extends GenericAuditEntity {

    private String name;

    @OneToMany(mappedBy = "maintainer", orphanRemoval = true)
    private Set<NavigationAid> navigationAids = new HashSet<>();

    public Set<NavigationAid> getNavigationAids() {
        return Collections.unmodifiableSet(navigationAids);
    }
    public void addNavigationAid(NavigationAid navigationAid) {
        navigationAids.add(navigationAid);
        navigationAid.setMaintainer(this);
    }
    public void removeNavigationAid(NavigationAid navigationAid) {
        navigationAids.remove(navigationAid);
        navigationAid.setMaintainer(null);
    }
}