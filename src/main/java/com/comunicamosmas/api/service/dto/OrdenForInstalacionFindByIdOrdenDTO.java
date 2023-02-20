package com.comunicamosmas.api.service.dto;

public class OrdenForInstalacionFindByIdOrdenDTO {
	private Long idOrden;
	private Long idContrato;
	private String nombreCliente;
	private String tecnologia;
	private String codigoMikrotik;
	private String velocidad;
	private Long idServicio;
	private Long idEmpresa;
	private String departamento;
	private String municipio; 
	private String barrio;
	private String direccion;
	
	public OrdenForInstalacionFindByIdOrdenDTO() {
		 
	}

	public OrdenForInstalacionFindByIdOrdenDTO(Long idOrden, Long idContrato, String nombreCliente, String tecnologia,
			String codigoMikrotik, String velocidad, Long idServicio, Long idEmpresa, String departamento,
			String municipio, String barrio, String direccion) {
		 
		this.idOrden = idOrden;
		this.idContrato = idContrato;
		this.nombreCliente = nombreCliente;
		this.tecnologia = tecnologia;
		this.codigoMikrotik = codigoMikrotik;
		this.velocidad = velocidad;
		this.idServicio = idServicio;
		this.idEmpresa = idEmpresa;
		this.departamento = departamento;
		this.municipio = municipio;
		this.barrio = barrio;
		this.direccion = direccion;
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

	public String getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	public String getCodigoMikrotik() {
		return codigoMikrotik;
	}

	public void setCodigoMikrotik(String codigoMikrotik) {
		this.codigoMikrotik = codigoMikrotik;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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

	@Override
	public String toString() {
		return "OrdenForInstalacionFindByIdOrdenDTO [idOrden=" + idOrden + ", idContrato=" + idContrato
				+ ", nombreCliente=" + nombreCliente + ", tecnologia=" + tecnologia + ", codigoMikrotik="
				+ codigoMikrotik + ", velocidad=" + velocidad + ", idServicio=" + idServicio + ", idEmpresa="
				+ idEmpresa + ", departamento=" + departamento + ", municipio=" + municipio + ", barrio=" + barrio
				+ ", direccion=" + direccion + "]";
	} 
	
}
