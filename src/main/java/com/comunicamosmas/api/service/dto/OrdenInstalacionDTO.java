package com.comunicamosmas.api.service.dto;

public class OrdenInstalacionDTO {
	private Long idOrden;
	private Long idContrato;
	private String nombreCliente;
	private String documento;
	private String nombreComercial;
	private String nombre;
	private String tipoTecnologia;
	
	public OrdenInstalacionDTO() {
		 
	}

	public OrdenInstalacionDTO(Long idOrden, Long idContrato, String nombreCliente, String documento,
			String nombreComercial, String nombre, String tipoTecnologia) {
		 
		this.idOrden = idOrden;
		this.idContrato = idContrato;
		this.nombreCliente = nombreCliente;
		this.documento = documento;
		this.nombreComercial = nombreComercial;
		this.nombre = nombre;
		this.tipoTecnologia = tipoTecnologia;
	}

	public Long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Long idOrden) {
		this.idOrden = idOrden;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
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

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
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
				+ nombreCliente + ", documento=" + documento + ", nombreComercial=" + nombreComercial + ", nombre="
				+ nombre + ", tipoTecnologia=" + tipoTecnologia + "]";
	}
	 

}
