package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sorteos")
@Entity
public class Sorteo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sorteo")
    private Long id;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    private String marca;

    private String motivo;

    private String premio;

    @Column(name = "meses_regala")
    private Long mesesRegala;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private Long fechf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public Long getMesesRegala() {
        return mesesRegala;
    }

    public void setMesesRegala(Long mesesRegala) {
        this.mesesRegala = mesesRegala;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getFechf() {
        return fechf;
    }

    public void setFechf(Long fechf) {
        this.fechf = fechf;
    }
}
