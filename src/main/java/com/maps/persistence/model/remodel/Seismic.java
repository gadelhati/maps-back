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
@Table(name = "seismic")
@PrimaryKeyJoinColumn(name="id")
public class Seismic extends Media {

    private String nameSeismicLine;
    private LocalDateTime startSeismicLineData;
    private LocalDateTime endSeismicLineData;
    private String qualityControl;
}