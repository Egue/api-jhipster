package com.comunicamosmas.api.service.dto;

import java.math.BigInteger;

public class DatosClienteDTO {
	
	public Integer idContrato;
	public Integer estrato;
	public String nombreCliente;
	public BigInteger documento; 
	public String celularA;
	public String celularB;
	public String mail;
	public String nombreTarifa;
	public Integer velocidad;
	public String valor;
	public String longDireccion; 
	public String latDireccion;
	public String longEstacion;
	public String latEstacion;  
	public String direccionServicio;
	
	
	public DatosClienteDTO() {
		 
	}
	public DatosClienteDTO(Integer idContrato, Integer estrato, String nombreCliente, BigInteger documento, String celularA,
			String celularB, String mail, String nombreTarifa, Integer velocidad, String valor, String longDireccion,
			String latDireccion, String longEstacion, String latEstacion, String direccionServicio) {
		 
		this.idContrato = idContrato;
		this.estrato = estrato;
		this.nombreCliente = nombreCliente;
		this.documento = documento;
		this.celularA = celularA;
		this.celularB = celularB;
		this.mail = mail;
		this.nombreTarifa = nombreTarifa;
		this.velocidad = velocidad;
		this.valor = valor;
		this.longDireccion = longDireccion;
		this.latDireccion = latDireccion;
		this.longEstacion = longEstacion;
		this.latEstacion = latEstacion;
		this.direccionServicio = direccionServicio;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getEstrato() {
		return estrato;
	}
	public void setEstrato(Integer estrato) {
		this.estrato = estrato;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public BigInteger getDocumento() {
		return documento;
	}
	public void setDocumento(BigInteger documento) {
		this.documento = documento;
	}
	public String getCelularA() {
		return celularA;
	}
	public void setCelularA(String celularA) {
		this.celularA = celularA;
	}
	public String getCelularB() {
		return celularB;
	}
	public void setCelularB(String celularB) {
		this.celularB = celularB;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNombreTarifa() {
		return nombreTarifa;
	}
	public void setNombreTarifa(String nombreTarifa) {
		this.nombreTarifa = nombreTarifa;
	}
	public Integer getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(Integer velocidad) {
		this.velocidad = velocidad;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getLongDireccion() {
		return longDireccion;
	}
	public void setLongDireccion(String longDireccion) {
		this.longDireccion = longDireccion;
	}
	public String getLatDireccion() {
		return latDireccion;
	}
	public void setLatDireccion(String latDireccion) {
		this.latDireccion = latDireccion;
	}
	public String getLongEstacion() {
		return longEstacion;
	}
	public void setLongEstacion(String longEstacion) {
		this.longEstacion = longEstacion;
	}
	public String getLatEstacion() {
		return latEstacion;
	}
	public void setLatEstacion(String latEstacion) {
		this.latEstacion = latEstacion;
	}
	public String getDireccionServicio() {
		return direccionServicio;
	}
	public void setDireccionServicio(String direccionServicio) {
		this.direccionServicio = direccionServicio;
	}
	@Override
	public String toString() {
		return "DatosClienteDTO [idContrato=" + idContrato + ", estrato=" + estrato + ", nombreCliente=" + nombreCliente
				+ ", documento=" + documento + ", celularA=" + celularA + ", celularB=" + celularB + ", mail=" + mail
				+ ", nombreTarifa=" + nombreTarifa + ", velocidad=" + velocidad + ", valor=" + valor
				+ ", longDireccion=" + longDireccion + ", latDireccion=" + latDireccion + ", longEstacion="
				+ longEstacion + ", latEstacion=" + latEstacion + ", direccionServicio=" + direccionServicio + "]";
	}
	
	

}
