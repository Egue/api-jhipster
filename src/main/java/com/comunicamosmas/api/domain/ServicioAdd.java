package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "servicios_add")
@Entity
public class ServicioAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicios_add")
    private Long id;

    @Column(name = "id_servicio")
    private Long idServicio;

    private String pucc;

    private String niff;

    private Long valor;

    private String nombre;

    private String marca;

    private Long estado;

    @Column(name = "pucc_iva")
    private String puccIva;

    @Column(name = "niff_iva")
    private String niffIva;

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

    public String getPucc() {
        return pucc;
    }

    public void setPucc(String pucc) {
        this.pucc = pucc;
    }

    public String getNiff() {
        return niff;
    }

    public void setNiff(String niff) {
        this.niff = niff;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getPuccIva() {
        return puccIva;
    }

    public void setPuccIva(String puccIva) {
        this.puccIva = puccIva;
    }

    public String getNiffIva() {
        return niffIva;
    }

    public void setNiffIva(String niffIva) {
        this.niffIva = niffIva;
    }
}
