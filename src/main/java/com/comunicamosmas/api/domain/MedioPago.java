package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "medios_pago.java")
@Entity
public class MedioPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medio_pago")
    private Long id;

    private String nombre;

    private Long estado;

    private Long comprobante;

    private Long reservado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getComprobante() {
        return comprobante;
    }

    public void setComprobante(Long comprobante) {
        this.comprobante = comprobante;
    }

    public Long getReservado() {
        return reservado;
    }

    public void setReservado(Long reservado) {
        this.reservado = reservado;
    }
}
