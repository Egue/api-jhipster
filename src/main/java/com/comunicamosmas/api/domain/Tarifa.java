package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tarifas")
@Entity
public class Tarifa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long id;

    @Column(name = "id_servicio")
    private Long idServicio;

    private Long valor;

    private String nombre;

    @Column(name = "cobro_mensual")
    private Long cobroMensual;

    @Column(name = "cobro_unico")
    private Long cobroUnico;

    private String marca;

    @Column(name = "valor_fijo")
    private Long valorFijo;

    private Long estado;

    @Column(name = "id_tecnologia")
    private Long idTecnologia;

    @Column(name = "numero_canales")
    private Long numeroCanales;

    private Long velocidad;

    @Column(name = "tipo_tarifa")
    private Long tipoTarifa;

    private String descripcion;

    @Column(name = "tipo_banda")
    private Long tipoBanda;

    @Column(name = "fecha_final")
    private String fechaFinal;

    @Column(name = "codigo_mikrotik")
    private String codigoMikrotik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCobroMensual() {
        return cobroMensual;
    }

    public void setCobroMensual(Long cobroMensual) {
        this.cobroMensual = cobroMensual;
    }

    public Long getCobroUnico() {
        return cobroUnico;
    }

    public void setCobroUnico(Long cobroUnico) {
        this.cobroUnico = cobroUnico;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getValorFijo() {
        return valorFijo;
    }

    public void setValorFijo(Long valorFijo) {
        this.valorFijo = valorFijo;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getIdTecnologia() {
        return idTecnologia;
    }

    public void setIdTecnologia(Long idTecnologia) {
        this.idTecnologia = idTecnologia;
    }

    public Long getNumeroCanales() {
        return numeroCanales;
    }

    public void setNumeroCanales(Long numeroCanales) {
        this.numeroCanales = numeroCanales;
    }

    public Long getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Long velocidad) {
        this.velocidad = velocidad;
    }

    public Long getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(Long tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipoBanda() {
        return tipoBanda;
    }

    public void setTipoBanda(Long tipoBanda) {
        this.tipoBanda = tipoBanda;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getCodigoMikrotik() {
        return codigoMikrotik;
    }

    public void setCodigoMikrotik(String codigoMikrotik) {
        this.codigoMikrotik = codigoMikrotik;
    }
}
