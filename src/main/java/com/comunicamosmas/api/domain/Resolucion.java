package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "resoluciones")
@Entity
public class Resolucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resolucion")
    private Long id;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "fecha_resolucion")
    private Long fechaResolucion;

    @Column(name = "rango_inicio")
    private String rangoInicio;

    @Column(name = "rango_final")
    private String rangoFinal;

    private String prefijo;

    @Column(name = "num_resolucion")
    private String numResolucion;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "api_id")
    private Long apiId;

    @Column(name = "api_key")
    private String apiKey;

    private String marca;

    private Long vigencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Long fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getRangoInicio() {
        return rangoInicio;
    }

    public void setRangoInicio(String rangoInicio) {
        this.rangoInicio = rangoInicio;
    }

    public String getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(String rangoFinal) {
        this.rangoFinal = rangoFinal;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getNumResolucion() {
        return numResolucion;
    }

    public void setNumResolucion(String numResolucion) {
        this.numResolucion = numResolucion;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getVigencia() {
        return vigencia;
    }

    public void setVigencia(Long vigencia) {
        this.vigencia = vigencia;
    }
}
