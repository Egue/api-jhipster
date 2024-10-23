package com.comunicamosmas.api.service.dto;

public class PortalWebSaveContrato{
    
    private String id_contrato;

    private String id_cliente;

    private Integer idServicio;

    private ContratosFirmasDTO.Datos datosContrato;

    public String getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(String id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public ContratosFirmasDTO.Datos getDatosContrato() {
        return datosContrato;
    }

    public void setDatosContrato(ContratosFirmasDTO.Datos datosContrato) {
        this.datosContrato = datosContrato;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    
}
