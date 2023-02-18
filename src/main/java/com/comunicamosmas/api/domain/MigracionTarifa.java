package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "migracion_tarifas")
@Entity
public class MigracionTarifa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_migracion")
    private Long id;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_tarifa_promo")
    private Long idTarifaPromo;

    @Column(name = "id_tarifa_general")
    private Long idTarifaGeneral;

    @Column(name = "id_tarifa_promo_new")
    private Long idTarifaPromoNew;

    @Column(name = "id_tarifa_general_new")
    private Long idTarifaGeneralNew;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_admin")
    private Long idAdmin;

    @Column(name = "id_orden")
    private Long idOrden;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_cliente")
    private String idCliente;

    private String lugar;

    private String fechaf;

    private String marca;

    private String justificacion;

    private Long estado;

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

    public Long getIdTarifaPromo() {
        return idTarifaPromo;
    }

    public void setIdTarifaPromo(Long idTarifaPromo) {
        this.idTarifaPromo = idTarifaPromo;
    }

    public Long getIdTarifaGeneral() {
        return idTarifaGeneral;
    }

    public void setIdTarifaGeneral(Long idTarifaGeneral) {
        this.idTarifaGeneral = idTarifaGeneral;
    }

    public Long getIdTarifaPromoNew() {
        return idTarifaPromoNew;
    }

    public void setIdTarifaPromoNew(Long idTarifaPromoNew) {
        this.idTarifaPromoNew = idTarifaPromoNew;
    }

    public Long getIdTarifaGeneralNew() {
        return idTarifaGeneralNew;
    }

    public void setIdTarifaGeneralNew(Long idTarifaGeneralNew) {
        this.idTarifaGeneralNew = idTarifaGeneralNew;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}
