package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "inventarios_articulos_items")
@Entity
public class InventarioArticuloItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo_item")
    private Long id;

    @Column(name = "id_articulo")
    private Long idArticulo;

    private String serialequipo;

    private String modelo;

    private String mac;

    private Long baja;

    private String nota;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_estacion")
    private Long idEstacion;

    @Column(name = "id_bodega")
    private Long idBodega;

    @Column(name = "fecha_transfiere")
    private Long fechaTransfiere;

    private String codigo;

    private String marca;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_comprador")
    private Long idComprador;

    @Column(name = "id_carga")
    private Long idCarga;

    @Column(name = "orden_temporal")
    private Long ordenTemporal;

    private Long esatadovisual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getSerialequipo() {
        return serialequipo;
    }

    public void setSerialequipo(String serialequipo) {
        this.serialequipo = serialequipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getBaja() {
        return baja;
    }

    public void setBaja(Long baja) {
        this.baja = baja;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Long idBodega) {
        this.idBodega = idBodega;
    }

    public Long getFechaTransfiere() {
        return fechaTransfiere;
    }

    public void setFechaTransfiere(Long fechaTransfiere) {
        this.fechaTransfiere = fechaTransfiere;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Long idComprador) {
        this.idComprador = idComprador;
    }

    public Long getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(Long idCarga) {
        this.idCarga = idCarga;
    }

    public Long getOrdenTemporal() {
        return ordenTemporal;
    }

    public void setOrdenTemporal(Long ordenTemporal) {
        this.ordenTemporal = ordenTemporal;
    }

    public Long getEsatadovisual() {
        return esatadovisual;
    }

    public void setEsatadovisual(Long esatadovisual) {
        this.esatadovisual = esatadovisual;
    }
}
