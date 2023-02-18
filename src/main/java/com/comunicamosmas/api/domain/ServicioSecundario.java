package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "servicios_segundarios")
@Entity
public class ServicioSecundario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    private String nombre;

    private Long estado;

    private String pucc;

    private String niff;

    private Long impocomsumo;

    @Column(name = "pucc_impocomsumo")
    private String puccImpocomsumo;

    @Column(name = "niff_iva")
    private String niffImpocomsumo;

    private String iva;

    private String marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public Long getImpocomsumo() {
        return impocomsumo;
    }

    public void setImpocomsumo(Long impocomsumo) {
        this.impocomsumo = impocomsumo;
    }

    public String getPuccImpocomsumo() {
        return puccImpocomsumo;
    }

    public void setPuccImpocomsumo(String puccImpocomsumo) {
        this.puccImpocomsumo = puccImpocomsumo;
    }

    public String getNiffImpocomsumo() {
        return niffImpocomsumo;
    }

    public void setNiffImpocomsumo(String niffImpocomsumo) {
        this.niffImpocomsumo = niffImpocomsumo;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
