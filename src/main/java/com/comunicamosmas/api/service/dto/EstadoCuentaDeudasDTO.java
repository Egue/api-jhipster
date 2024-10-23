package com.comunicamosmas.api.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.comunicamosmas.api.domain.FinancieroNc;

public class EstadoCuentaDeudasDTO {

	private Integer idDeuda;
	private Integer factura;
	private String periodo;
	private String generador;
	private String concepto;
	private Double valor;
	private Float abono;
	private List<PagosEstadoCuentaDTO> pagos;
	private List<SaldoFavorDTO> saldosFavor;
	private List<FinancieroNc> notasCredito;
	
	public EstadoCuentaDeudasDTO() {
		 
	}

	public EstadoCuentaDeudasDTO(Integer idDeuda, Integer factura, String periodo, String generador, Double valor, Float abono,
			List<PagosEstadoCuentaDTO> pagos, List<SaldoFavorDTO> saldosFavor,
			List<FinancieroNc> notasCredito) {
	 
		this.idDeuda = idDeuda;
		this.factura = factura;
		this.periodo = periodo;
		this.generador = generador;
		this.valor = valor;
		this.pagos = pagos;
		this.abono = abono;
		this.saldosFavor = saldosFavor;
		this.notasCredito = notasCredito;
	}

	public Integer getIdDeuda() {
		return idDeuda;
	}

	public void setIdDeuda(Integer idDeuda) {
		this.idDeuda = idDeuda;
	}

	public Integer getFactura() {
		return factura;
	}

	public void setFactura(Integer factura) {
		this.factura = factura;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getGenerador() {
		return generador;
	}

	public void setGenerador(String generador) {
		this.generador = generador;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<PagosEstadoCuentaDTO> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagosEstadoCuentaDTO> pagos) {
		this.pagos = pagos;
	}

	public List<SaldoFavorDTO> getSaldosFavor() {
		return saldosFavor;
	}

	public void setSaldosFavor(List<SaldoFavorDTO> saldosFavor) {
		this.saldosFavor = saldosFavor;
	}

	 
 
	public Float getAbono() {
		return abono;
	}

	public void setAbono(Float abono) {
		this.abono = abono;
	}

	public List<FinancieroNc> getNotasCredito() {
		return notasCredito;
	}

	public void setNotasCredito(List<FinancieroNc> notasCredito) {
		this.notasCredito = notasCredito;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	
	
	
}
