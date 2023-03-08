package com.comunicamosmas.api.service.dto;

public class ListContratoDTO {
	private String nombreMunicipio;
	private String nombreServicio;
	private Integer idContrato;
	private String barrio;
	private String direccion;
	private String estado;
	
	public ListContratoDTO() {
		
	}

	public ListContratoDTO(String nombreMunicipio, String nombreServicio, Integer idContrato, String barrio,
			String direccion, String estado) {
		 
		this.nombreMunicipio = nombreMunicipio;
		this.nombreServicio = nombreServicio;
		this.idContrato = idContrato;
		this.barrio = barrio;
		this.direccion = direccion;
		this.estado = estado;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "ListContratoDTO [nombreMunicipio=" + nombreMunicipio + ", nombreServicio=" + nombreServicio
				+ ", idContrato=" + idContrato + ", barrio=" + barrio + ", direccion=" + direccion + ", estado="
				+ estado + "]";
		
		
	}
	

}
