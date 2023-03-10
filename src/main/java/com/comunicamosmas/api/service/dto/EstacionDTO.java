package com.comunicamosmas.api.service.dto;

public class EstacionDTO {

	private Integer id;
	
	private String nombreEstacion;
	
	private String tipo;
	
	private String nombreComercial;
	
	private String nombreServicio;
	
	private String nombreCiudad;
	
	private String nombreZona;
	
	private String codigo;
	
	private String ip;

	public EstacionDTO() {
		 
	}

	public EstacionDTO(Integer id, String nombreEstacion, String tipo, String nombreComercial, String nombreServicio,
			String nombreCiudad, String nombreZona, String codigo, String ip) {
		 
		this.id = id;
		this.nombreEstacion = nombreEstacion;
		this.tipo = tipo;
		this.nombreComercial = nombreComercial;
		this.nombreServicio = nombreServicio;
		this.nombreCiudad = nombreCiudad;
		this.nombreZona = nombreZona;
		this.codigo = codigo;
		this.ip = ip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreEstacion() {
		return nombreEstacion;
	}

	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "EstacionDTO [id=" + id + ", nombreEstacion=" + nombreEstacion + ", tipo=" + tipo + ", nombreComercial="
				+ nombreComercial + ", nombreServicio=" + nombreServicio + ", nombreCiudad=" + nombreCiudad
				+ ", nombreZona=" + nombreZona + ", codigo=" + codigo + ", ip=" + ip + "]";
	}
	
	
	
	
	
}
