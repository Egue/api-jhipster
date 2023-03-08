package com.comunicamosmas.api.service.dto;

public class SimpleQueueFindReusoDTO {

	private Integer idContrato;
	
	private Integer idEstacion;
	
	private Integer velocidad;
	
	private String nombreTecnologia;
	
	private Integer idWinmaxPass;

	public SimpleQueueFindReusoDTO() {
	 
	}

	public SimpleQueueFindReusoDTO(Integer idContrato, Integer idEstacion, Integer velocidad, String nombreTecnologia,
			Integer idWinmaxPass) {
	 
		this.idContrato = idContrato;
		this.idEstacion = idEstacion;
		this.velocidad = velocidad;
		this.nombreTecnologia = nombreTecnologia;
		this.idWinmaxPass = idWinmaxPass;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Integer idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Integer getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Integer velocidad) {
		this.velocidad = velocidad;
	}

	public String getNombreTecnologia() {
		return nombreTecnologia;
	}

	public void setNombreTecnologia(String nombreTecnologia) {
		this.nombreTecnologia = nombreTecnologia;
	}

	public Integer getIdWinmaxPass() {
		return idWinmaxPass;
	}

	public void setIdWinmaxPass(Integer idWinmaxPass) {
		this.idWinmaxPass = idWinmaxPass;
	}

	@Override
	public String toString() {
		return "SimpleQueueFindReusoDTO [idContrato=" + idContrato + ", idEstacion=" + idEstacion + ", velocidad="
				+ velocidad + ", nombreTecnologia=" + nombreTecnologia + ", idWinmaxPass=" + idWinmaxPass + "]";
	}
	
	
	
	
}
