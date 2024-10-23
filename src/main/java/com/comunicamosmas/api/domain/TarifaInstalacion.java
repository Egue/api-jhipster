package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tarifas_instalacion")
@Entity
public class TarifaInstalacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long id;

    @Column(name = "id_servicio")
    private Long idServicio;

    private Long valor;

    private String nombre;

    private Long estado;

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

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}
