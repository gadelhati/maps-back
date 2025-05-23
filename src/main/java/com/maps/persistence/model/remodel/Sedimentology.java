package com.maps.persistence.model.remodel;

import com.maps.persistence.model.remodel.Media;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@DiscriminatorValue("SEDIMENTOLOGY")
@Table(name = "sedimentologies")
public class Sedimentology extends Media {

    private Integer cod_amostra_geologica;
    private Integer num_analise;
    private String b;
    private String co;
    private String cod_laboratorio;
    private String cr;
    private LocalDateTime data_analise;
    private LocalDateTime data_remessa;
    private String e;
    private Integer espessura;
    private String f;
    private String floculacao;
    private String g;
    private String gc;
    private String idade_geologica;
    private String l;
    private String maturidade;
    private String metodo_analise_mineral;
    private String metodo_datacao;
    private String metodologia_c1;
    private String metodologia_c2;
    private String metodologia_d1;
    private String metodologia_d2;
    private String metodologia_e;
    private String metodologia_f;
    private String metodologia_m1;
    private String metodologia_m2;
    private String metodologia_p1;
    private String metodologia_p2;
    private String metodologia_r;
    private String metodologia_t;
    private String morfom_a;
    private String morfom_e;
    private String morfom_f;
    private String o1;
    private String o2;
    private String p;
    private String s;
    private Integer peso_analise;
    private Integer percentual_carbonato;
    @Column(length = 300)
    private String obs_analise;
    private Integer per_05_dist_gran;
    private Integer per_16_dist_gran;
    private Integer per_25_dist_gran;
    private Integer per_50_dist_gran;
    private Integer per_75_dist_gran;
    private Integer per_84_dist_gran;
    private Integer per_95_dist_gran;
    private Integer par_assimetria;
    private Integer par_curtose;
    private Integer par_desvio_padrao;
    private Integer par_diametro_medio;
    private Integer par_mediana;
    private Integer per_areia;
    private Integer per_argila;
    private Integer per_cascalho;
    private Integer per_silte;
    private String cod_banco;
    private Integer num_arquivo;
    private Integer comprimento_coletado;
}
