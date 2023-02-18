package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tmp_datos_contrato")
@Entity
public class TmpDatoContrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "c")
    private String c;

    @Column(name = "d")
    private String d;

    @Column(name = "e")
    private String e;

    @Column(name = "f")
    private String f;

    @Column(name = "g")
    private String g;

    @Column(name = "h")
    private String h;

    @Column(name = "i")
    private String i;

    @Column(name = "j")
    private String j;

    @Column(name = "k")
    private String k;

    @Column(name = "l")
    private String l;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getC() {
        return c;
    }

    public void setC(String C) {
        c = C;
    }

    public String getD() {
        return d;
    }

    public void setD(String D) {
        d = D;
    }

    public String getE() {
        return e;
    }

    public void setE(String E) {
        e = E;
    }

    public String getF() {
        return f;
    }

    public void setF(String F) {
        f = F;
    }

    public String getG() {
        return g;
    }

    public void setG(String G) {
        g = G;
    }

    public String getH() {
        return h;
    }

    public void setH(String H) {
        h = H;
    }

    public String getI() {
        return i;
    }

    public void setI(String I) {
        i = I;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String J) {
        j = J;
    }

    public String getK() {
        return k;
    }

    public void setK(String K) {
        k = K;
    }

    public String getL() {
        return l;
    }

    public void setL(String L) {
        l = L;
    }
}
