package com.comunicamosmas.api.service.dto;

import java.time.LocalDateTime;

public class OrderDetalleDTO {
      private String idOrden;
    private String nombreEstado;
    private String causaSolicitud;
    private String idContrato;
    private String fechafRegistro;
    private String refiere;
    private String tipoCliente;
    private String cliente;
    private String documento;
    private String barrio;
    private String direccion;
    private String telefonoContacto;
    private String nombreTecnologia;
    private String nota;
    private String estado;
    private String notaFinal;
    private String anulada;
    private String visitasFallidas;
    private Integer userEjecuta;

     
     
    public void setFechafRegistro(String fechafRegistro) {
        this.fechafRegistro = fechafRegistro;
    }
    public Integer getUserEjecuta() {
        return userEjecuta;
    }
    public void setUserEjecuta(Integer userEjecuta) {
        this.userEjecuta = userEjecuta;
    }
    public String getIdOrden() {
        return idOrden;
    }
    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }
    public String getNombreEstado() {
        return nombreEstado;
    }
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    public String getCausaSolicitud() {
        return causaSolicitud;
    }
    public void setCausaSolicitud(String causaSolicitud) {
        this.causaSolicitud = causaSolicitud;
    }
    public String getIdContrato() {
        return idContrato;
    }
    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }
    public String getFechafRegistro() {
        return fechafRegistro;
    }
    public void setFechaRegistro(String fechafRegistro) {
        this.fechafRegistro = fechafRegistro;
    }
    public String getRefiere() {
        return refiere;
    }
    public void setRefiere(String refiere) {
        this.refiere = refiere;
    }
    public String getTipoCliente() {
        return tipoCliente;
    }
    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getBarrio() {
        return barrio;
    }
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefonoContacto() {
        return telefonoContacto;
    }
    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
    public String getNombreTecnologia() {
        return nombreTecnologia;
    }
    public void setNombreTecnologia(String nombreTecnologia) {
        this.nombreTecnologia = nombreTecnologia;
    }
    public String getNota() {
        return nota;
    }
    public void setNota(String nota) {
        this.nota = nota;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNotaFinal() {
        return notaFinal;
    }
    public void setNotaFinal(String notaFinal) {
        this.notaFinal = notaFinal;
    }
    public String getAnulada() {
        return anulada;
    }
    public void setAnulada(String anulada) {
        this.anulada = anulada;
    }
    public String getVisitasFallidas() {
        return visitasFallidas;
    }
    public void setVisitasFallidas(String visitasFallidas) {
        this.visitasFallidas = visitasFallidas;
    }

    
}
