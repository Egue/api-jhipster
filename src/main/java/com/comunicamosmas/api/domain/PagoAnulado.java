package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pagos_anulados")
@Entity
public class PagoAnulado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anulacion")
    private Long id;

    @Column(name = "numero_recibo")
    private Long numeroRecibo;

    @Column(name = "id_justificacion")
    private Long idJustificacion;

    @Column(name = "id_cajero")
    private Long idCajero;

    @Column(name = "id_admin")
    private Long idAdmin;

    private String marca;

    private String fechaf;

    private String comentario;

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

    private Float valor;

    private String lugar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(Long numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Long getIdJustificacion() {
        return idJustificacion;
    }

    public void setIdJustificacion(Long idJustificacion) {
        this.idJustificacion = idJustificacion;
    }

    public Long getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Long idCajero) {
        this.idCajero = idCajero;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
