package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "clausulas_permanencia")
@Entity
public class ClausulaPermanencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clausula")
    private Long id;

    private Long tipo;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "mes_1")
    private String mes1;

    @Column(name = "mes_2")
    private Long mes2;

    @Column(name = "mes_3")
    private Long mes3;

    @Column(name = "mes_4")
    private Long mes4;

    @Column(name = "mes_5")
    private Long mes5;

    @Column(name = "mes_6")
    private Long mes6;

    @Column(name = "mes_7")
    private Long mes7;

    @Column(name = "mes_8")
    private Long mes8;

    @Column(name = "mes_9")
    private Long mes9;

    @Column(name = "mes_10")
    private Long mes10;

    @Column(name = "mes_11")
    private Long mes11;

    @Column(name = "mes_12")
    private Long mes12;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getMes1() {
        return mes1;
    }

    public void setMes1(String mes1) {
        this.mes1 = mes1;
    }

    public Long getMes2() {
        return mes2;
    }

    public void setMes2(Long mes2) {
        this.mes2 = mes2;
    }

    public Long getMes3() {
        return mes3;
    }

    public void setMes3(Long mes3) {
        this.mes3 = mes3;
    }

    public Long getMes4() {
        return mes4;
    }

    public void setMes4(Long mes4) {
        this.mes4 = mes4;
    }

    public Long getMes5() {
        return mes5;
    }

    public void setMes5(Long mes5) {
        this.mes5 = mes5;
    }

    public Long getMes6() {
        return mes6;
    }

    public void setMes6(Long mes6) {
        this.mes6 = mes6;
    }

    public Long getMes7() {
        return mes7;
    }

    public void setMes7(Long mes7) {
        this.mes7 = mes7;
    }

    public Long getMes8() {
        return mes8;
    }

    public void setMes8(Long mes8) {
        this.mes8 = mes8;
    }

    public Long getMes9() {
        return mes9;
    }

    public void setMes9(Long mes9) {
        this.mes9 = mes9;
    }

    public Long getMes10() {
        return mes10;
    }

    public void setMes10(Long mes10) {
        this.mes10 = mes10;
    }

    public Long getMes11() {
        return mes11;
    }

    public void setMes11(Long mes11) {
        this.mes11 = mes11;
    }

    public Long getMes12() {
        return mes12;
    }

    public void setMes12(Long mes12) {
        this.mes12 = mes12;
    }
}
