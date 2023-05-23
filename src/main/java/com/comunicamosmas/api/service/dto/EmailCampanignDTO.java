package com.comunicamosmas.api.service.dto;

import java.util.Date;

public class EmailCampanignDTO {

	private Integer id;
	
	private String anno;
	
	private Date fecha;
	
	private String fechaLimitePago;
	
	private String mes;
	
	private String nombre;
	
	private String nombreComercial;

	private String fechaCorte;
	
	private String estado;
	

	public EmailCampanignDTO() {
		 
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getAnno() {
		return anno;
	}



	public void setAnno(String anno) {
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



	public String getMes() {
		return mes;
	}



	public void setMes(String mes) {
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



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getFechaCorte() {
		return fechaCorte;
	}



	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
 
	
	
}
