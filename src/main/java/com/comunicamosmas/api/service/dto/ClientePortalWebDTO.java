package com.comunicamosmas.api.service.dto;

public class ClientePortalWebDTO {
    private Long idCliente;

    private String tipoCliente;

    private String razonSocial;

    private String nombreComercial;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String nombrePrimer;

    private String nombreSegundo;

    private String nombresRep;

    private String apellidosRep;

    private String idDocumento;

    private Long documento;

    private Long dv;

    private Long telefono;
 
    private String celularA;
 
    private Long idOperaA;
 
    private String celularB;
 
    private Long idOperaB;

    private String mail;

    

    @Override
    public String toString() {
        return "ClientePortalWebDTO [id=" + idCliente + ", tipoCliente=" + tipoCliente + ", razonCliente=" + razonSocial
                + ", nombreComercial=" + nombreComercial + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
                + apellidoMaterno + ", nombrePrimer=" + nombrePrimer + ", nombreSegundo=" + nombreSegundo
                + ", nombresRep=" + nombresRep + ", apellidosRep=" + apellidosRep + ", idDocumento=" + idDocumento
                + ", documento=" + documento + ", dv=" + dv + ", telefono=" + telefono + ", celularA=" + celularA
                + ", idOperaA=" + idOperaA + ", celularB=" + celularB + ", idOperaB=" + idOperaB + ", mail=" + mail
                + "]";
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial= razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombrePrimer() {
        return nombrePrimer;
    }

    public void setNombrePrimer(String nombrePrimer) {
        this.nombrePrimer = nombrePrimer;
    }

    public String getNombreSegundo() {
        return nombreSegundo;
    }

    public void setNombreSegundo(String nombreSegundo) {
        this.nombreSegundo = nombreSegundo;
    }

    public String getNombresRep() {
        return nombresRep;
    }

    public void setNombresRep(String nombresRep) {
        this.nombresRep = nombresRep;
    }

    public String getApellidosRep() {
        return apellidosRep;
    }

    public void setApellidosRep(String apellidosRep) {
        this.apellidosRep = apellidosRep;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public Long getDv() {
        return dv;
    }

    public void setDv(Long dv) {
        this.dv = dv;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCelularA() {
        return celularA;
    }

    public void setCelularA(String celularA) {
        this.celularA = celularA;
    }

    public Long getIdOperaA() {
        return idOperaA;
    }

    public void setIdOperaA(Long idOperaA) {
        this.idOperaA = idOperaA;
    }

    public String getCelularB() {
        return celularB;
    }

    public void setCelularB(String celularB) {
        this.celularB = celularB;
    }

    public Long getIdOperaB() {
        return idOperaB;
    }

    public void setIdOperaB(Long idOperaB) {
        this.idOperaB = idOperaB;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    

}
