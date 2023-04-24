package com.comunicamosmas.api.service.dto;

import java.util.Date;

public class EmailCampanignDTO {

	private Integer id;
	
	private Integer anno;
	
	private Date fecha;
	
	private String fechaLimitePago;
	
	private Integer mes;
	
	private String nombre;
	
	private String nombreComercial;
	
	

	public EmailCampanignDTO() {
		 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFechaLimitePago() {
		return fechaLimitePago;
	}

	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	
	
}
