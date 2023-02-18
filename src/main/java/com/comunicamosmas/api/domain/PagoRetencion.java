package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pagos_retenciones")
@Entity
public class PagoRetencion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "id_deuda")
    private Long idDeuda;

    @Column(name = "id_pago")
    private Long id_pago;

    private String turno;

    private Long retefuente;

    private Long reteica;

    private Long bomberil;

    private Long reteiva;

    private Long otros;

    @Column(name = "id_recibo_caja")
    private Long idReciboCaja;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    private String grupo;

    private String marca;

    @Column(name = "retefuente_t")
    private Long retefuenteT;

    @Column(name = "reteica_t")
    private Long reteicaT;

    @Column(name = "bomberil_t")
    private Long bomberilT;

    @Column(name = "reteiva_t")
    private Long reteivaT;

    @Column(name = "otros_t")
    private Long otrosT;

    private Long fechaf;

    @Column(name = "id_cajero")
    private Long idCajero;

    @Column(name = "id_medio_pago")
    private Long idMedioPago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public Long getId_pago() {
        return id_pago;
    }

    public void setId_pago(Long id_pago) {
        this.id_pago = id_pago;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Long getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(Long retefuente) {
        this.retefuente = retefuente;
    }

    public Long getReteica() {
        return reteica;
    }

    public void setReteica(Long reteica) {
        this.reteica = reteica;
    }

    public Long getBomberil() {
        return bomberil;
    }

    public void setBomberil(Long bomberil) {
        this.bomberil = bomberil;
    }

    public Long getReteiva() {
        return reteiva;
    }

    public void setReteiva(Long reteiva) {
        this.reteiva = reteiva;
    }

    public Long getOtros() {
        return otros;
    }

    public void setOtros(Long otros) {
        this.otros = otros;
    }

    public Long getIdReciboCaja() {
        return idReciboCaja;
    }

    public void setIdReciboCaja(Long idReciboCaja) {
        this.idReciboCaja = idReciboCaja;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getRetefuenteT() {
        return retefuenteT;
    }

    public void setRetefuenteT(Long retefuenteT) {
        this.retefuenteT = retefuenteT;
    }

    public Long getReteicaT() {
        return reteicaT;
    }

    public void setReteicaT(Long reteicaT) {
        this.reteicaT = reteicaT;
    }

    public Long getBomberilT() {
        return bomberilT;
    }

    public void setBomberilT(Long bomberilT) {
        this.bomberilT = bomberilT;
    }

    public Long getReteivaT() {
        return reteivaT;
    }

    public void setReteivaT(Long reteivaT) {
        this.reteivaT = reteivaT;
    }

    public Long getOtrosT() {
        return otrosT;
    }

    public void setOtrosT(Long otrosT) {
        this.otrosT = otrosT;
    }

    public Long getFechaf() {
        return fechaf;
    }

    public void setFechaf(Long fechaf) {
        this.fechaf = fechaf;
    }

    public Long getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Long idCajero) {
        this.idCajero = idCajero;
    }

    public Long getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }
}
