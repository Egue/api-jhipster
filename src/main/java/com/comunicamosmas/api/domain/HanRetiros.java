package com.comunicamosmas.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 

@Table(name = "han_retiros")
@Entity
public class HanRetiros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private String descripcion;

    @Column(name="id_user")
    private Long idUser;
    
    @Column(name="created_at")
    //@Temporal(TemporalType.TIMESTAMP)
    private String createdAt;

    @Column(name="id_contrato")
    private Long idContrato;

    private String estado;

    @Column(name="cierre_fecha"  , nullable = true)
    private String cierreFecha;

    @Column(name="url_documento")
    private String urlDocumento;

    @Column(name="id_padre")
    private Long idPadre;

    @Column(name="id_servicio")
    private Long idServicio;

    private String reportado;
    

    
    public String getReportado() {
        return reportado;
    }

    public void setReportado(String reportado) {
        this.reportado = reportado;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
         
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            this.createdAt = LocalDateTime.now().format(formatter); 
        
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCierreFecha() {
        return cierreFecha;
    }

    public void setCierreFecha( String cierreFecha) { 

        this.cierreFecha = cierreFecha;
        
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    

}
