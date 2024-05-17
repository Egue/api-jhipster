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
    private Long idcliente;

    private String pais;

    private String departamento;

    private String municipio;

    private String barrio;

    private String tipo;

    @Column(name = "a_tipo")
    private String atipo;

    @Column(name = "a_numero")
    private Long anumero;

    @Column(name = "a_letra")
    private String aletra;

    @Column(name = "b_tipo")
    private String btipo;

    @Column(name = "b_numero")
    private Long bnumero;

    @Column(name = "b_letra")
    private String bletra;

    private Long numero;

    private String latitud;

    private String longitud;

    private String nota;

    private Long estado;

    //private String marca;

    private Long rara;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
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

    public String getAtipo() {
        return atipo;
    }

    public void setAtipo(String atipo) {
        this.atipo = atipo;
    }

    public Long getAnumero() {
        return anumero;
    }

    public void setAnumero(Long anumero) {
        this.anumero = anumero;
    }

    public String getAletra() {
        return aletra;
    }

    public void setAletra(String aletra) {
        this.aletra = aletra;
    }

    public String getBtipo() {
        return btipo;
    }

    public void setBtipo(String btipo) {
        this.btipo = btipo;
    }

    public Long getBnumero() {
        return bnumero;
    }

    public void setBnumero(Long bnumero) {
        this.bnumero = bnumero;
    }

    public String getBletra() {
        return bletra;
    }

    public void setBletra(String bletra) {
        this.bletra = bletra;
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

    public Long getRara() {
        return rara;
    }

    public void setRara(Long rara) {
        this.rara = rara;
    }

    @Override
    public String toString() {
        return "Direccion [id=" + id + ", idcliente=" + idcliente + ", pais=" + pais + ", departamento=" + departamento
                + ", municipio=" + municipio + ", barrio=" + barrio + ", tipo=" + tipo + ", atipo=" + atipo
                + ", anumero=" + anumero + ", aletra=" + aletra + ", btipo=" + btipo + ", bnumero=" + bnumero
                + ", bletra=" + bletra + ", numero=" + numero + ", latitud=" + latitud + ", longitud=" + longitud
                + ", nota=" + nota + ", estado=" + estado + ", rara=" + rara + "]";
    }
 
    
}
