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
@Table(name = "mediaCategories", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class MediaCategory extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @OneToMany(mappedBy = "mediaCategory", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Media> medias = new HashSet<>();

    public Set<Media> getMedias() {
        return Collections.unmodifiableSet(medias);
    }
    public void addMedia(Media media) {
        medias.add(media);
        media.setMediaCategory(this);
    }
    public void removeMedia(Media media) {
        medias.remove(media);
        media.setMediaCategory(null);
    }
}