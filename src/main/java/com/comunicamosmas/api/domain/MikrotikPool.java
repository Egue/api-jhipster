package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mikrotik_pool")
@Entity
public class MikrotikPool implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pool")
    private Long id;

    @Column(name = "id_estacion")
    private Long idEstacion;

    @Column(name = "nombre_pool")
    private String nombrePool;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getNombrePool() {
        return nombrePool;
    }

    public void setNombrePool(String nombrePool) {
        this.nombrePool = nombrePool;
    }
}
