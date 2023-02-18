package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "direcciones")
@Entity
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @Column(name = "id_cliente")
    private Long idCliente;

    private String pais;

    private String departamento;

    private String municipio;

    private String barrio;

    private String tipo;

    @Column(name = "a_tipo")
    private String aTipo;

    @Column(name = "a_numero")
    private Long aNumero;

    @Column(name = "a_letra")
    private String aLetra;

    @Column(name = "b_tipo")
    private String bTipo;

    @Column(name = "b_numero")
    private Long bNumero;

    @Column(name = "b_letra")
    private String bLetra;

    private Long numero;

    private String latitud;

    private String longitud;

    private String nota;

    private Long estado;

    private String marca;

    private Long rara;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getaTipo() {
        return aTipo;
    }

    public void setaTipo(String aTipo) {
        this.aTipo = aTipo;
    }

    public Long getaNumero() {
        return aNumero;
    }

    public void setaNumero(Long aNumero) {
        this.aNumero = aNumero;
    }

    public String getaLetra() {
        return aLetra;
    }

    public void setaLetra(String aLetra) {
        this.aLetra = aLetra;
    }

    public String getbTipo() {
        return bTipo;
    }

    public void setbTipo(String bTipo) {
        this.bTipo = bTipo;
    }

    public Long getbNumero() {
        return bNumero;
    }

    public void setbNumero(Long bNumero) {
        this.bNumero = bNumero;
    }

    public String getbLetra() {
        return bLetra;
    }

    public void setbLetra(String bLetra) {
        this.bLetra = bLetra;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getRara() {
        return rara;
    }

    public void setRara(Long rara) {
        this.rara = rara;
    }
}
