package com.comunicamosmas.api.service.dto;

public class ContratoInfoFacturaDTO {
	
	private Integer idCliente;
	
	private String documento;
	
	private String celular;
	
	private String nombreCliente;
	
	private String direccion;

	public ContratoInfoFacturaDTO(Integer idCliente, String documento, String celular, String nombreCliente,
			String direccion) { 
		this.idCliente = idCliente;
		this.documento = documento;
		this.celular = celular;
		this.nombreCliente = nombreCliente;
		this.direccion = direccion;
	}

	public ContratoInfoFacturaDTO() {
		  
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "ContratoInfoFacturaDTO [idCliente=" + idCliente + ", documento=" + documento + ", celular=" + celular
				+ ", nombreCliente=" + nombreCliente + ", direccion=" + direccion + "]";
	}
	
	
	
	

}
