package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

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
@Table(name = "meteorologySurfaces")
@PrimaryKeyJoinColumn(name="id")
public class MeteorologySurface extends Media {

    private String observer;
    private LocalDateTime dateTime;
    private String station;
    @Embedded
    private Section0 section0;
    @Embedded
    private Section1 section1;
    @Embedded
    private Section2 section2;
    @Embedded
    private Section5 section5;
}