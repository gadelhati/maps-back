package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class GeologicalSample extends GenericAuditEntity {

    private int cod_amostr_geolog;
    private int cod_estacao;
    private int area_origem;
    private int arrasto_velocidade;
    private int arrasto_direcao;
    private int arrasto_duracao;
    private int foto_amostra;
    private int foto_fundo;
    private int side_scan;
    private int tipo_amostra;
    private int volume_amostra;
    private int peso_amostra;
    private int cor_amostra;
    private int textura_amostra;
    private boolean concrecoes;
    private boolean conchas;
    private boolean plantas;
    private boolean animais;
    private int poluicao;
    private int tenca_principal;
    private int tenca_complementar;
    private int tenca_amostra;
    private int obs_analise_preliminar;
    private int obs_identificacao;
    private int sondagem;
    private int estrutura;
    private int num_sec_amostra;
    private int comprimento_testemunho;
    private int cor_fundo;
    private int cor_topo;
    private int numnac;
    private int ctrlqc_geo;
    private int grau_selecao;
    private int grau_arredondamento;
}