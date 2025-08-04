package com.comunicamosmas.api.service.dto;

public class InfoPagosDTO {
    private Integer idReciboPago;

    private Float valor;

    private String fecha;

    private String token;

    private String tipoPago;

    private String cajero;

    public Integer getIdReciboPago() {
        return idReciboPago;
    }

    public void setIdReciboPago(Integer idReciboPago) {
        this.idReciboPago = idReciboPago;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    

}
