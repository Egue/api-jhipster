package com.comunicamosmas.api.service.dto;

public class TarifasForCambioDTO {
	private Integer idTarifa;
	private String nombre;
	public TarifasForCambioDTO() {
		 
	}
	public TarifasForCambioDTO(Integer idTarifa, String nombre) {
		 
		this.idTarifa = idTarifa;
		this.nombre = nombre;
	}
	public Integer getIdTarifa() {
		return idTarifa;
	}
	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "TarifasForCambioDTO [idTarifa=" + idTarifa + ", nombre=" + nombre + "]";
	}
	

}
