package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "cache_estratos_traslado")
@Entity
public class CacheEstratoTraslado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cache")
    private Long id;

    @Column(name = "estrato_actual")
    private Long estratoActual;

    @Column(name = "nuevo_estrato")
    private Long nuevoEstrato;

    @Column(name = "id_orden")
    private Long idOrden;

    private String marca;

    @Column(name = "id_contrato")
    private Long idContrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstratoActual() {
        return estratoActual;
    }

    public void setEstratoActual(Long estratoActual) {
        this.estratoActual = estratoActual;
    }

    public Long getNuevoEstrato() {
        return nuevoEstrato;
    }

    public void setNuevoEstrato(Long nuevoEstrato) {
        this.nuevoEstrato = nuevoEstrato;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }
}
