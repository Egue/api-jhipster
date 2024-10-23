package com.comunicamosmas.api.service.dto;

import java.io.Serializable;
 

 

public class EmailCampaignDetalleDTO implements Serializable{
 


	public EmailCampaignDetalleDTO() {
	}

	private static final long serialVersionUID = 71231313L;

	private Integer id;
	
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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
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
	
	
	
}
	 
	

