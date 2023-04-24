package com.comunicamosmas.api.service.dto;

public class EmailCampaignDetalleDTO {

	private Integer id;
	
	private Integer idContrato;
	
	private Integer idCliente;
	
	private String email;
	
	private String response;
	
	private String procesado;
	
	private String nombreServicio;
	
	private String tipoCliente;
	
	private String nombreCliente;
	
	

	public EmailCampaignDetalleDTO() {
		 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
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
	
	
	
}
