package com.comunicamosmas.api.service.dto;

public class OrdenByContratoDTO {
	
	private Integer idContrato;
	private Integer idOrden;
	private Integer numeroA;
	private Integer numeroB;
	private String tipoOrden;
	private String origen;
	private String registro;
	private String asignacion;
	private String ejecucion;
	private String userAsigna;
	private String userEjecuta;
	public OrdenByContratoDTO() {
		 
	}
	public OrdenByContratoDTO(Integer idContrato, Integer idOrden, Integer numeroA, Integer numeroB, String tipoOrden,
			String origen, String registro, String asignacion, String ejecucion, String userAsigna,
			String userEjecuta) {
		 
		this.idContrato = idContrato;
		this.idOrden = idOrden;
		this.numeroA = numeroA;
		this.numeroB = numeroB;
		this.tipoOrden = tipoOrden;
		this.origen = origen;
		this.registro = registro;
		this.asignacion = asignacion;
		this.ejecucion = ejecucion;
		this.userAsigna = userAsigna;
		this.userEjecuta = userEjecuta;
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
	public Integer getNumeroA() {
		return numeroA;
	}
	public void setNumeroA(Integer numeroA) {
		this.numeroA = numeroA;
	}
	public Integer getNumeroB() {
		return numeroB;
	}
	public void setNumeroB(Integer numeroB) {
		this.numeroB = numeroB;
	}
	public String getTipoOrden() {
		return tipoOrden;
	}
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getAsignacion() {
		return asignacion;
	}
	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}
	public String getEjecucion() {
		return ejecucion;
	}
	public void setEjecucion(String ejecucion) {
		this.ejecucion = ejecucion;
	}
	public String getUserAsigna() {
		return userAsigna;
	}
	public void setUserAsigna(String userAsigna) {
		this.userAsigna = userAsigna;
	}
	public String getUserEjecuta() {
		return userEjecuta;
	}
	public void setUserEjecuta(String userEjecuta) {
		this.userEjecuta = userEjecuta;
	}
	@Override
	public String toString() {
		return "OrdenByContratoDTO [idContrato=" + idContrato + ", idOrden=" + idOrden + ", numeroA=" + numeroA
				+ ", numeroB=" + numeroB + ", tipoOrden=" + tipoOrden + ", origen=" + origen + ", registro=" + registro
				+ ", asignacion=" + asignacion + ", ejecucion=" + ejecucion + ", userAsigna=" + userAsigna
				+ ", userEjecuta=" + userEjecuta + "]";
	}
	
	
	
	

}
