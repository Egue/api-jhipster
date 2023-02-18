package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ordenes_internet_ssid")
@Entity
public class OrdenInternetSsid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "id_orden")
    private Long idOrden;

    @Column(name = "id_contrato")
    private Long idContrato;

    private String ssid;

    private String ssidp;

    private String ssidaw;

    private String ssidapw;

    private String marca;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_usuario_registra")
    private Long idUsuarioRegistra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSsidp() {
        return ssidp;
    }

    public void setSsidp(String ssidp) {
        this.ssidp = ssidp;
    }

    public String getSsidaw() {
        return ssidaw;
    }

    public void setSsidaw(String ssidaw) {
        this.ssidaw = ssidaw;
    }

    public String getSsidapw() {
        return ssidapw;
    }

    public void setSsidapw(String ssidapw) {
        this.ssidapw = ssidapw;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(Long idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }
}
