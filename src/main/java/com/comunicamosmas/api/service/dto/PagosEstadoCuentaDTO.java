package com.comunicamosmas.api.service.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class PagosEstadoCuentaDTO {
	
	private Integer IdPago;
	private Integer reciboCaja;
	private Float valorCobrado;
	private Timestamp marca;
	
	
	
	public PagosEstadoCuentaDTO() {
		 
	}



	public PagosEstadoCuentaDTO(Integer idPago, Integer reciboCaja, Float valorCobrado, Timestamp marca) {
	 
		IdPago = idPago;
		this.reciboCaja = reciboCaja;
		this.valorCobrado = valorCobrado;
		this.marca = marca;
	}



	public Integer getIdPago() {
		return IdPago;
	}



	public void setIdPago(Integer idPago) {
		IdPago = idPago;
	}



	public Integer getReciboCaja() {
		return reciboCaja;
	}



	public void setReciboCaja(Integer reciboCaja) {
		this.reciboCaja = reciboCaja;
	}



	public Float getValorCobrado() {
		return valorCobrado;
	}



	public void setValorCobrado(Float valorCobrado) {
		this.valorCobrado = valorCobrado;
	}



	public Timestamp getMarca() {
		return marca;
	}



	public void setMarca(Timestamp marca) {
		this.marca = marca;
	}
	
	
	
	

}
