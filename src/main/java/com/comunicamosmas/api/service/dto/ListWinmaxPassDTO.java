package com.comunicamosmas.api.service.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class ListWinmaxPassDTO {
		
	private Integer idContrato;
	private String contratoEstado;
	private String tipoCliente;
	private String nombreCliente;
	private String usuario;
	private String pass;
	private Timestamp marca;
	private String nombreTecnologia;
	private Integer velocidad;
	private String nombreTarifa;
	private Integer valor;
	private String codigoMikrotik;
	
	public ListWinmaxPassDTO() {
		 
	}

	public ListWinmaxPassDTO(Integer idContrato, String contratoEstado, String tipoCliente, String nombreCliente,
			String usuario, String pass, Timestamp marca, String nombreTecnologia, Integer velocidad,
			String nombreTarifa, Integer valor, String codigoMikrotik) {
		super();
		this.idContrato = idContrato;
		this.contratoEstado = contratoEstado;
		this.tipoCliente = tipoCliente;
		this.nombreCliente = nombreCliente;
		this.usuario = usuario;
		this.pass = pass;
		this.marca = marca;
		this.nombreTecnologia = nombreTecnologia;
		this.velocidad = velocidad;
		this.nombreTarifa = nombreTarifa;
		this.valor = valor;
		this.codigoMikrotik = codigoMikrotik;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getContratoEstado() {
		return contratoEstado;
	}

	public void setContratoEstado(String contratoEstado) {
		this.contratoEstado = contratoEstado;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Timestamp getMarca() {
		return marca;
	}

	public void setMarca(Timestamp marca) {
		this.marca = marca;
	}

	public String getNombreTecnologia() {
		return nombreTecnologia;
	}

	public void setNombreTecnologia(String nombreTecnologia) {
		this.nombreTecnologia = nombreTecnologia;
	}

	public Integer getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Integer velocidad) {
		this.velocidad = velocidad;
	}

	public String getNombreTarifa() {
		return nombreTarifa;
	}

	public void setNombreTarifa(String nombreTarifa) {
		this.nombreTarifa = nombreTarifa;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public String getCodigoMikrotik() {
		return codigoMikrotik;
	}

	public void setCodigoMikrotik(String codigoMikrotik) {
		this.codigoMikrotik = codigoMikrotik;
	}

	@Override
	public String toString() {
		return "ListWinmaxPassDTO [idContrato=" + idContrato + ", contratoEstado=" + contratoEstado + ", tipoCliente="
				+ tipoCliente + ", nombreCliente=" + nombreCliente + ", usuario=" + usuario + ", pass=" + pass
				+ ", marca=" + marca + ", nombreTecnologia=" + nombreTecnologia + ", velocidad=" + velocidad
				+ ", nombreTarifa=" + nombreTarifa + ", valor=" + valor + ", codigoMikrotik=" + codigoMikrotik + "]";
	}
 
 
	
	
	

}
