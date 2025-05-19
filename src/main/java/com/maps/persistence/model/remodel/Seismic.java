package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("SEISMIC")
public class Seismic extends Media {

    private String nameSeismicLine;
    private LocalDateTime startSeismicLineData;
    private LocalDateTime endSeismicLineData;
    private String qualityControl;
}