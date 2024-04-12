package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class UpperAir extends GenericAuditEntity {

    private int cod_estacao;
    private int alt;
    private int dbc;
    private int pressao;
    private int dd;
    private int fff;
    private int ido;
    private int temp;
    private int ur;
    private int hora;
    private int ascensao;
    private int orvalho;
    private int ctrlqc_pressao;
    private int ctrlqc_vento;
    private int ctrlqc_temp;
    private int ctrlqc_orvalho;
    private int ctrlqc_ur;
    private int ctrlqc_alt;
    private int ctrlqc_dd;
    private int ctrlqc_fff;
}