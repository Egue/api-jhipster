package com.comunicamosmas.api.service.dto;

public class RespuestaGeneracionPDFFactura {
    
    private String pathPDF;

    private String pathXML;

    private String pathZIP;

    private String prefijo;

    private String nit;  

    private String razon_social;

    private String factura;

    private String codigoDocumento;

    private String nameComercial;

    private String destinatario;

    private String mesGenerado;

    private String fechaLimite;

    private String valorPagar;

    private String logoPublico;

    public String getPathPDF() {
        return pathPDF;
    }

    
    public String getDestinatario() {
        return destinatario;
    }


    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }


    public String getMesGenerado() {
        return mesGenerado;
    }


    public void setMesGenerado(String mesGenerado) {
        this.mesGenerado = mesGenerado;
    }


    public String getFechaLimite() {
        return fechaLimite;
    }


    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }


    public String getValorPagar() {
        return valorPagar;
    }


    public void setValorPagar(String valorPagar) {
        this.valorPagar = valorPagar;
    }


    public void setPathPDF(String pathPDF) {
        this.pathPDF = pathPDF;
    }

    public String getPathXML() {
        return pathXML;
    }

    public void setPathXML(String pathXML) {
        this.pathXML = pathXML;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

     

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getNameComercial() {
        return nameComercial;
    }

    public void setNameComercial(String nameComercial) {
        this.nameComercial = nameComercial;
    }

    @Override
    public String toString() {
        return "RespuestaGeneracionPDFFactura [pathPDF=" + pathPDF + ", pathXML=" + pathXML + ", prefijo=" + prefijo
                + ", nit=" + nit +  ", razon_social=" + razon_social + ", factura=" + factura
                + ", codigoDocumento=" + codigoDocumento + ", nameComercial=" + nameComercial + "]";
    }


    public String getLogoPublico() {
        return logoPublico;
    }


    public void setLogoPublico(String logoPublico) {
        this.logoPublico = logoPublico;
    }


    public String getPathZIP() {
        return pathZIP;
    }


    public void setPathZIP(String pathZIP) {
        this.pathZIP = pathZIP;
    }

    
}
