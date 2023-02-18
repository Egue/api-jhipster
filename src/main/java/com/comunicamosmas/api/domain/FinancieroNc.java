package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "financiero_nc")
@Entity
public class FinancieroNc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nc")
    private Long id;

    @Column(name = "numero_nc")
    private Long numeroNc;

    private Long factura;

    @Column(name = "id_deuda")
    private Long idDeuda;

    private Double valor;

    @Column(name = "valor_base")
    private Double valorBase;

    @Column(name = "valor_iva")
    private Double valorIva;

    private String lugar;

    private String fechaf;

    @Column(name = "id_admin")
    private Long idAdmin;

    @Column(name = "id_contrato_afecta")
    private Long idContratoAfecta;

    @Column(name = "id_servicio_afecta")
    private Long idServicioAfecta;

    @Column(name = "id_cliente_afecta")
    private Long idClienteAfecta;

    @Column(name = "id_ciudad_afecta")
    private Long idCiudadAfecta;

    @Column(name = "id_empresa_afecta")
    private Long idEmpresaAfecta;

    @Column(name = "id_justificacion")
    private Long idJustificacion;

    private String comentario;

    private String marca;

    private Long electronica;

    @Column(name = "electronica_api")
    private String electronicaApi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroNc() {
        return numeroNc;
    }

    public void setNumeroNc(Long numeroNc) {
        this.numeroNc = numeroNc;
    }

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public Long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
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

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdContratoAfecta() {
        return idContratoAfecta;
    }

    public void setIdContratoAfecta(Long idContratoAfecta) {
        this.idContratoAfecta = idContratoAfecta;
    }

    public Long getIdServicioAfecta() {
        return idServicioAfecta;
    }

    public void setIdServicioAfecta(Long idServicioAfecta) {
        this.idServicioAfecta = idServicioAfecta;
    }

    public Long getIdClienteAfecta() {
        return idClienteAfecta;
    }

    public void setIdClienteAfecta(Long idClienteAfecta) {
        this.idClienteAfecta = idClienteAfecta;
    }

    public Long getIdCiudadAfecta() {
        return idCiudadAfecta;
    }

    public void setIdCiudadAfecta(Long idCiudadAfecta) {
        this.idCiudadAfecta = idCiudadAfecta;
    }

    public Long getIdEmpresaAfecta() {
        return idEmpresaAfecta;
    }

    public void setIdEmpresaAfecta(Long idEmpresaAfecta) {
        this.idEmpresaAfecta = idEmpresaAfecta;
    }

    public Long getIdJustificacion() {
        return idJustificacion;
    }

    public void setIdJustificacion(Long idJustificacion) {
        this.idJustificacion = idJustificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getElectronica() {
        return electronica;
    }

    public void setElectronica(Long electronica) {
        this.electronica = electronica;
    }

    public String getElectronicaApi() {
        return electronicaApi;
    }

    public void setElectronicaApi(String electronicaApi) {
        this.electronicaApi = electronicaApi;
    }
}
