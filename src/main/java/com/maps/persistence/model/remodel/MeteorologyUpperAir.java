package com.maps.persistence.model.remodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

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
@Table(name = "meteorologyUpperAirs")
@PrimaryKeyJoinColumn(name="id")
public class MeteorologyUpperAir extends Media {

//    private Integer cod_estacao;
    private Integer alt;
    private Integer dbc;
    private Integer pressao;
    private Integer dd;
    private Integer fff;
    private Integer ido;
    private Integer temp;
    private Integer ur;
    private LocalTime hora;
    private Integer ascensao;
    private Integer orvalho;
    private String ctrlqc_pressao;
    private String ctrlqc_vento;
    private String ctrlqc_temp;
    private String ctrlqc_orvalho;
    private String ctrlqc_ur;
    private String ctrlqc_alt;
    private String ctrlqc_dd;
    private String ctrlqc_fff;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "station_id")
//    private Station station;
}