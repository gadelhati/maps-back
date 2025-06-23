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

import java.time.LocalDateTime;
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
@Table(name = "researches", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Research extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;

    @OneToMany(mappedBy = "research", orphanRemoval = true)
    private Set<ResearchMember> researchMembers = new HashSet<>();
    @OneToMany(mappedBy = "research", orphanRemoval = true)
    private Set<EquipmentDeployment> equipmentDeployments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    public Set<ResearchMember> getResearchMembers() {
        return Collections.unmodifiableSet(researchMembers);
    }
    public void addResearchMember(ResearchMember member) {
        researchMembers.add(member);
        member.setResearch(this);
    }
    public void removeResearchMember(ResearchMember member) {
        researchMembers.remove(member);
        member.setResearch(null);
    }

    public Set<EquipmentDeployment> getEquipmentDeployments() {
        return Collections.unmodifiableSet(equipmentDeployments);
    }
    public void addEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.add(deployment);
        deployment.setResearch(this);
    }
    public void removeEquipmentDeployment(EquipmentDeployment deployment) {
        equipmentDeployments.remove(deployment);
        deployment.setResearch(null);
    }
}