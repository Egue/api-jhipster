package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "admin")
@Entity
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "estado_pdf")
    private Long estadoPdf;

    private Long cadacuanto;

    private Long c1;

    private Long c2;

    private Long c3;

    private Long c4;

    private Long c5;

    private Long c6;

    private Long c7;

    @Column(name = "	id_saludocupa")
    private Long idSaludocupa;

    @Column(name = "id_contabilidad")
    private Long idContabilidad;

    private Long mantenimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstadoPdf() {
        return estadoPdf;
    }

    public void setEstadoPdf(Long estadoPdf) {
        this.estadoPdf = estadoPdf;
    }

    public Long getCadacuanto() {
        return cadacuanto;
    }

    public void setCadacuanto(Long cadacuanto) {
        this.cadacuanto = cadacuanto;
    }

    public Long getC1() {
        return c1;
    }

    public void setC1(Long c1) {
        this.c1 = c1;
    }

    public Long getC2() {
        return c2;
    }

    public void setC2(Long c2) {
        this.c2 = c2;
    }

    public Long getC3() {
        return c3;
    }

    public void setC3(Long c3) {
        this.c3 = c3;
    }

    public Long getC4() {
        return c4;
    }

    public void setC4(Long c4) {
        this.c4 = c4;
    }

    public Long getC5() {
        return c5;
    }

    public void setC5(Long c5) {
        this.c5 = c5;
    }

    public Long getC6() {
        return c6;
    }

    public void setC6(Long c6) {
        this.c6 = c6;
    }

    public Long getC7() {
        return c7;
    }

    public void setC7(Long c7) {
        this.c7 = c7;
    }

    public Long getIdSaludocupa() {
        return idSaludocupa;
    }

    public void setIdSaludocupa(Long idSaludocupa) {
        this.idSaludocupa = idSaludocupa;
    }

    public Long getIdContabilidad() {
        return idContabilidad;
    }

    public void setIdContabilidad(Long idContabilidad) {
        this.idContabilidad = idContabilidad;
    }

    public Long getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Long mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
}
