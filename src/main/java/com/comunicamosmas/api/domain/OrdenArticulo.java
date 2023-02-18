package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ordenes_articulos")
@Entity
public class OrdenArticulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indice")
    private Long id;

    @Column(name = "id_orden")
    private Long idOrden;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_estacion")
    private Long idEstacion;

    @Column(name = "id_articulo")
    private Long idArticulo;

    @Column(name = "cantidad_empresa")
    private Long cantidadEmpresa;

    @Column(name = "cantidad_usuario")
    private Long cantidadUsuario;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private Long serialinv;

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

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Long getCantidadEmpresa() {
        return cantidadEmpresa;
    }

    public void setCantidadEmpresa(Long cantidadEmpresa) {
        this.cantidadEmpresa = cantidadEmpresa;
    }

    public Long getCantidadUsuario() {
        return cantidadUsuario;
    }

    public void setCantidadUsuario(Long cantidadUsuario) {
        this.cantidadUsuario = cantidadUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getSerialinv() {
        return serialinv;
    }

    public void setSerialinv(Long serialinv) {
        this.serialinv = serialinv;
    }
}
