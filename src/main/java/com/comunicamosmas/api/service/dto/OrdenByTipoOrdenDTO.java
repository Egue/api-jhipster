package com.comunicamosmas.api.service.dto;

public class OrdenByTipoOrdenDTO {
	private Integer idContrato;
	private Integer idOrden;
	private String registro;
	private String nombreCliente;
	private String nombreEstacion;
	private String nota;
	public OrdenByTipoOrdenDTO() {
		 
	}
	public OrdenByTipoOrdenDTO(Integer idContrato, Integer idOrden, String registro, String nombreCliente,
			String nombreEstacion, String nota) {
		super();
		this.idContrato = idContrato;
		this.idOrden = idOrden;
		this.registro = registro;
		this.nombreCliente = nombreCliente;
		this.nombreEstacion = nombreEstacion;
		this.nota = nota;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNombreEstacion() {
		return nombreEstacion;
	}
	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	@Override
	public String toString() {
		return "OrdenByTipoOrdenDTO [idContrato=" + idContrato + ", idOrden=" + idOrden + ", registro=" + registro
				+ ", nombreCliente=" + nombreCliente + ", nombreEstacion=" + nombreEstacion + ", nota=" + nota + "]";
	} 
	
	
	
	
	

}
