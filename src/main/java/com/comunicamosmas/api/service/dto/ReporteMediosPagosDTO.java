package com.comunicamosmas.api.service.dto;

public class ReporteMediosPagosDTO {
     
    private int codigo;
    private int rc;
    private String ciudad;
    private String servicio;
    private String cliente;
    private String cajero;
    private float valor;
    private String marca;
    private String origen;
    private int contrato;
    private String payments;
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getRc() {
        return rc;
    }
    public void setRc(int rc) {
        this.rc = rc;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getServicio() {
        return servicio;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getCajero() {
        return cajero;
    }
    public void setCajero(String cajero) {
        this.cajero = cajero;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public int getContrato() {
        return contrato;
    }
    public void setContrato(int contrato) {
        this.contrato = contrato;
    }
    public String getPayments() {
        return payments;
    }
    public void setPayments(String payments) {
        this.payments = payments;
    }

    
}
