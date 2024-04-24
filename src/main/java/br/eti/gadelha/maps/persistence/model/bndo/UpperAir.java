package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class UpperAir extends GenericAuditEntity {

    private Integer cod_estacao;
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
    private Integer ctrlqc_pressao;
    private int ctrlqc_vento;
    private int ctrlqc_temp;
    private int ctrlqc_orvalho;
    private int ctrlqc_ur;
    private int ctrlqc_alt;
    private int ctrlqc_dd;
    private int ctrlqc_fff;
}