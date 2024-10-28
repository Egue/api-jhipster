package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mikrotik_tarifas_reuso")
@Entity
public class MikrotikTarifaReuso implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa_reuso")
    private Long id;

    @Column(name = "nombre_padre")
    private String nombrePadre;

    private String comment;

    private String subida;

    private String bajada;

    private String estado;

    private Long tipo;

    private Long reuso;

    private String priority;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "id_estacion")
    private Long idEstacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSubida() {
        return subida;
    }

    public void setSubida(String subida) {
        this.subida = subida;
    }

    public String getBajada() {
        return bajada;
    }

    public void setBajada(String bajada) {
        this.bajada = bajada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public Long getReuso() {
        return reuso;
    }

    public void setReuso(Long reuso) {
        this.reuso = reuso;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }
}
