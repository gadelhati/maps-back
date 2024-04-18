package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Station extends GenericAuditEntity {

    private Integer code;
//    private Integer cod_plataforma;
//    private Integer cod_equipamento;
//    private Integer cod_comissao;
//    private Integer cod_tipo_estacao;
//    private Integer cod_midia;
    private String number;
    private String name;
    private LocalDateTime dateTime;
    private Integer latitude;
    private Integer longitude;
    @Column(columnDefinition = "geography")
    private Point point;//latitude and longitude
    private Integer quad_marsden;
    private Integer sub_quad_marsden_1;
    private Integer quad_wmo;
    private Integer sub_sub_quad_marsden;
    private Integer prof_coleta_geo;
    private String obs;
    private String cod_sigilo;
//    private Integer cod_datum;
    private Integer ctrlqc_posicao;
    private Integer ctrlqc_data_hora;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "platform", nullable = true)
    private Platform platform;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "equipment", nullable = true)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "commission", nullable = true)
    private Commission commission;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "stationCategory", nullable = true)
    private StationCategory stationCategory;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "media", nullable = true)
    private Media media;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "datum", nullable = true)
    private Datum datum;
}