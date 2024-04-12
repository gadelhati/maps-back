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
import java.util.Collection;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Commission extends GenericAuditEntity {

    private String code;
    private String codeProjeto;
    private String codeLevantamento;
    private String cod_instituicao_coordenadora;
    private String cod_instituicao_responsavel;

    private String nome_cruzeiro;   // == pernada?
    private String num_cruzeiro;    // == pernada?
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String latBottommost;
    private String latTopmost;
    @Column(columnDefinition = "geography")
    private Point ne;//lat_topmost and long_rightmost
    private String longLeftmost;
    private String longRightmost;
    @Column(columnDefinition = "geography")
    private Point sw;//lat_bottommost and long_leftmost
    private String porto_partida;
    private String porto_chegada;
    private String areaName;
    private String maximumDepthArea;
    private String minimumDepthArea;
    private String maximumCollectionDepth;
    private String minimumCollectionDepth;
    private String totalSizeMedia;
    private String qualificacao_dados;//se cada equipamento validou os dados
    private String hFolder;
    private String obs;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "project", nullable = true)
    private Project project;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hydrographicSurvey", nullable = true)
    private HydrographicSurvey hydrographicSurvey;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "commission_institution", joinColumns = @JoinColumn(name = "commission_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "id"))
    private Collection<Institution> institution;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "commission_harbors", joinColumns = @JoinColumn(name = "commission_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "harbor_id", referencedColumnName = "id"))
    private Collection<Harbor> harbor;
}