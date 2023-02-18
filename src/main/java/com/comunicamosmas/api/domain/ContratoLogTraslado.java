package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "contratos_log_traslado")
@Entity
public class ContratoLogTraslado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_traslado")
    private Long id;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private String marca;

    @Column(name = "id_dirr_factura_ant")
    private Long idDirrFacturaAnt;

    @Column(name = "id_dirr_servicio_ant")
    private Long idDirrServicioAnt;

    @Column(name = "id_dirr_factura_sig")
    private Long idDirrFacturaSig;

    @Column(name = "id_dirr_servicio_sig")
    private Long idDirrServicioSig;

    @Column(name = "id_orden")
    private Long idOrden;

    @Column(name = "valor_traslado")
    private Long valorTraslado;

    @Column(name = "id_zona_ant")
    private Long idZonaAnt;

    @Column(name = "id_zona_sig")
    private Long idZonaSig;

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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdDirrFacturaAnt() {
        return idDirrFacturaAnt;
    }

    public void setIdDirrFacturaAnt(Long idDirrFacturaAnt) {
        this.idDirrFacturaAnt = idDirrFacturaAnt;
    }

    public Long getIdDirrServicioAnt() {
        return idDirrServicioAnt;
    }

    public void setIdDirrServicioAnt(Long idDirrServicioAnt) {
        this.idDirrServicioAnt = idDirrServicioAnt;
    }

    public Long getIdDirrFacturaSig() {
        return idDirrFacturaSig;
    }

    public void setIdDirrFacturaSig(Long idDirrFacturaSig) {
        this.idDirrFacturaSig = idDirrFacturaSig;
    }

    public Long getIdDirrServicioSig() {
        return idDirrServicioSig;
    }

    public void setIdDirrServicioSig(Long idDirrServicioSig) {
        this.idDirrServicioSig = idDirrServicioSig;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public Long getValorTraslado() {
        return valorTraslado;
    }

    public void setValorTraslado(Long valorTraslado) {
        this.valorTraslado = valorTraslado;
    }

    public Long getIdZonaAnt() {
        return idZonaAnt;
    }

    public void setIdZonaAnt(Long idZonaAnt) {
        this.idZonaAnt = idZonaAnt;
    }

    public Long getIdZonaSig() {
        return idZonaSig;
    }

    public void setIdZonaSig(Long idZonaSig) {
        this.idZonaSig = idZonaSig;
    }
}
