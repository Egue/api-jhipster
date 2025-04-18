package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "lista_municipios")
@Entity
public class ListaMunicipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    private String municipio;

    private Long estado;

    @Column(name="departamento_id")
    private Long departamentoid;

    private Long dian;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getDepartamento_id() {
        return departamentoid;
    }

    public void setDepartamento_id(Long departamentoid) {
        this.departamentoid = departamentoid;
    }

    public Long getDian() {
        return dian;
    }

    public void setDian(Long dian) {
        this.dian = dian;
    }
}
