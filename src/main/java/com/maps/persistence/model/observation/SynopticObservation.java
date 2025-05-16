package com.maps.persistence.model.observation;

//import br.eti.gadelha.exception.enumeration.EnumMiMiMjMj;
import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Entity;
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
public class SynopticObservation extends GenericAuditEntity {

    private String observer;
    private LocalDateTime dateTime;
    private String station;

//    private EnumMiMiMjMj MiMiMjMj;//station identification group
    private String DDDDDD;//ship’s call sign
    private String A1;
    private String bw;
    private String nbnbnb;

    private String YY;
    private String GG;
    private String iw;

    private String II;
    private String iii;//WMO número 9
    private String LaLaLa;
    private String Qc;
    private String LoLOLOLO;

    private String iR;
    private String iX;
    private String h;
    private String VV;
    private String N;
    private String dd;
    private String ff;
    private String fff;
    private String sn1;
    private String TTT;
    private String sn2;
    private String TdTdTd;
    private String UUU;
    private String P0P0P0P0;
    private String PPPP;
    private String a;
    private String ppp;
    private String RRR;
    private String iR6;
    private String ww;
    private String W1W2;
    private String Nh;
    private String CL;
    private String CM;
    private String CH;
    private String GGgg;

    private String Ds;
    private String Vs;
    private String Ss;
    private String TwTwTw;
    private String PwaPwa;
    private String HwaHwa;
    private String PwPw;
    private String HwHw;
    private String dw1dw1;
    private String dw2dw2;
    private String Pw1Pw1;
    private String Hw1Hw1;
    private String Pw2Pw2;
    private String Hw2Hw2;
    private String Iese;
    private String EsEs;
    private String Rs;
    private String HwaHwaHwa;
    private String sw;
    private String TbTbTb;
    private String ci;
    private String Si;
    private String bi;
    private String Di;
    private String zi;

//    private String sn1;
    private String TxTxTx;
//    private String sn2;
    private String TnTnTn;
    private String _89;
    private String P24P24P24;

    private String ichw;
    private String icM;
    private String cs;
    private String icF;
    private String icp;
    private String icQ;
}