package com.comunicamosmas.api.domainMongo;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facturas_emitidas")
public class FacturasEmitidas implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;
	
	private String factura;
	
	private Integer idCliente;
	
	private String email;
	
	private String response;
	
	private String procesado;
	
	private String nombreServicio;
	
	private String tipoCliente;
	
	private String nombreCliente;
	
	private Integer idCampaign;

	private String origen;

	private String nameDocument;

	private String fecha_limite;

	private String fecha_corte;
 
 
 

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getIdCampaign() {
        return idCampaign;
    }

    public void setIdCampaign(Integer idCampaign) {
        this.idCampaign = idCampaign;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getNameDocument() {
        return nameDocument;
    }

    public void setNameDocument(String nameDocument) {
        this.nameDocument = nameDocument;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public String getFecha_corte() {
        return fecha_corte;
    }

    public void setFecha_corte(String fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    @Override
    public String toString() {
        return "FacturasEmitidas [id=" + id + ", factura=" + factura + ", idCliente=" + idCliente + ", email=" + email
                + ", response=" + response + ", procesado=" + procesado + ", nombreServicio=" + nombreServicio
                + ", tipoCliente=" + tipoCliente + ", nombreCliente=" + nombreCliente + ", idCampaign=" + idCampaign
                + ", origen=" + origen + ", nameDocument=" + nameDocument + ", fecha_limite=" + fecha_limite
                + ", fecha_corte=" + fecha_corte + "]";
    }

    
    
}
