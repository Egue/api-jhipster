package com.comunicamosmas.api.service.dto;

public class ClienteContratoDTO {
    private Long idContrato;
    private String tipoCliente;
    private String nameServicio;
    private String direccion;
    private Double parcial ;
    private Double total;

    public ClienteContratoDTO(Long idContrato , String tipoCliente , String nameServicio, String direccion , Double parcial , Double total){
        this.idContrato = idContrato;
        this.tipoCliente = tipoCliente;
        this.nameServicio = nameServicio;
        this.direccion = direccion;
        this.parcial = parcial;
        this.total = total;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNameServicio() {
        return nameServicio;
    }

    public void setNameServicio(String nameServicio) {
        this.nameServicio = nameServicio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getParcial() {
        return parcial;
    }

    public void setParcial(Double parcial) {
        this.parcial = parcial;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
