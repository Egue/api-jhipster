package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "contratos_historico_estados")
@Entity
public class ContratoHistoricoEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_estado_estaba")
    private Long idEstadoEstaba;

    @Column(name = "id_estado_entra")
    private Long idEstadoEntra;

    private Long fechaf;

    private String marca;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_ciudad")
    private Long idCiudad;

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

    public Long getIdEstadoEstaba() {
        return idEstadoEstaba;
    }

    public void setIdEstadoEstaba(Long idEstadoEstaba) {
        this.idEstadoEstaba = idEstadoEstaba;
    }

    public Long getIdEstadoEntra() {
        return idEstadoEntra;
    }

    public void setIdEstadoEntra(Long idEstadoEntra) {
        this.idEstadoEntra = idEstadoEntra;
    }

    public Long getFechaf() {
        return fechaf;
    }

    public void setFechaf(Long fechaf) {
        this.fechaf = fechaf;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }
}
