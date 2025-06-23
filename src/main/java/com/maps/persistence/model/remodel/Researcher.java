package com.maps.persistence.model.remodel;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "researchers")
public class Researcher extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    @Email
    private String email;

    @OneToMany(mappedBy = "researcher", orphanRemoval = true)
    private Set<ResearchMember> researchMembers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    public Set<ResearchMember> getResearchMembers() {
        return Collections.unmodifiableSet(researchMembers);
    }
    public void addResearchMember(ResearchMember member) {
        researchMembers.add(member);
        member.setResearcher(this);
    }
    public void removeResearchMember(ResearchMember member) {
        researchMembers.remove(member);
        member.setResearcher(null);
    }
}