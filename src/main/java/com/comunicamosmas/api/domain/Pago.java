package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pagos")
@Entity
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "id_recibo_caja")
    private Long idReciboCaja;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_deuda")
    private Long idDeuda;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_cajero")
    private Long idCajero;

    private Long fechaf;

    @Column(name = "id_medio_pago")
    private Long idMedioPago;

    private String comprobante;

    @Column(name = "valor_dado")
    private Float valorDado;

    @Column(name = "valor_cobro")
    private Float valorCobro;

    @Column(name = "valor_vueltas	")
    private Float valorVueltas;

    @Column(name = "valor_redondeo")
    private Float valorRedondeo;

    private Long estado;

    private Long marca;

    @Column(name = "anula_id_usuario")
    private Long anulaIdUsuario;

    @Column(name = "anula_marca")
    private String anulaMarca;

    @Column(name = "anula_justifica")
    private String anulaJustifica;

    private String lugar;

    private String turno;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "mes_servicio")
    private Long mesServicio;

    private Long instalacion;

    private Long reconexion;

    private Long materiales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReciboCaja() {
        return idReciboCaja;
    }

    public void setIdReciboCaja(Long idReciboCaja) {
        this.idReciboCaja = idReciboCaja;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Long idCajero) {
        this.idCajero = idCajero;
    }

    public Long getFechaf() {
        return fechaf;
    }

    public void setFechaf(Long fechaf) {
        this.fechaf = fechaf;
    }

    public Long getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public Float getValorDado() {
        return valorDado;
    }

    public void setValorDado(Float valorDado) {
        this.valorDado = valorDado;
    }

    public Float getValorCobro() {
        return valorCobro;
    }

    public void setValorCobro(Float valorCobro) {
        this.valorCobro = valorCobro;
    }

    public Float getValorVueltas() {
        return valorVueltas;
    }

    public void setValorVueltas(Float valorVueltas) {
        this.valorVueltas = valorVueltas;
    }

    public Float getValorRedondeo() {
        return valorRedondeo;
    }

    public void setValorRedondeo(Float valorRedondeo) {
        this.valorRedondeo = valorRedondeo;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getMarca() {
        return marca;
    }

    public void setMarca(Long marca) {
        this.marca = marca;
    }

    public Long getAnulaIdUsuario() {
        return anulaIdUsuario;
    }

    public void setAnulaIdUsuario(Long anulaIdUsuario) {
        this.anulaIdUsuario = anulaIdUsuario;
    }

    public String getAnulaMarca() {
        return anulaMarca;
    }

    public void setAnulaMarca(String anulaMarca) {
        this.anulaMarca = anulaMarca;
    }

    public String getAnulaJustifica() {
        return anulaJustifica;
    }

    public void setAnulaJustifica(String anulaJustifica) {
        this.anulaJustifica = anulaJustifica;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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

    public Long getMesServicio() {
        return mesServicio;
    }

    public void setMesServicio(Long mesServicio) {
        this.mesServicio = mesServicio;
    }

    public Long getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Long instalacion) {
        this.instalacion = instalacion;
    }

    public Long getReconexion() {
        return reconexion;
    }

    public void setReconexion(Long reconexion) {
        this.reconexion = reconexion;
    }

    public Long getMateriales() {
        return materiales;
    }

    public void setMateriales(Long materiales) {
        this.materiales = materiales;
    }
}
