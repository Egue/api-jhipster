package com.comunicamosmas.api.service.dto;

public class OrdenInstalacionDTO {
	private Integer idOrden;
	private Integer idContrato;
	private String nombreCliente;
	private String documento; 
	private String nombre;
	private String tipoTecnologia;
	
	public OrdenInstalacionDTO() {
		 
	}

	public OrdenInstalacionDTO(Integer idOrden, Integer idContrato, String nombreCliente, String documento,
			 String nombre, String tipoTecnologia) {
		 
		this.idOrden = idOrden;
		this.idContrato = idContrato;
		this.nombreCliente = nombreCliente;
		this.documento = documento; 
		this.nombre = nombre;
		this.tipoTecnologia = tipoTecnologia;
	}

	public Integer getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	 

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoTecnologia() {
		return tipoTecnologia;
	}

	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	@Override
	public String toString() {
		return "OrdenInstalacionDTO [idOrden=" + idOrden + ", idContrato=" + idContrato + ", nombreCliente="
				+ nombreCliente + ", documento=" + documento + ",  nombre="
				+ nombre + ", tipoTecnologia=" + tipoTecnologia + "]";
	}
	 

}
