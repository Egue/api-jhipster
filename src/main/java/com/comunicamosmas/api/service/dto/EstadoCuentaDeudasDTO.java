package com.comunicamosmas.api.service.dto;

import java.util.ArrayList;

public class EstadoCuentaDeudasDTO {

	private Integer idDeuda;
	private Integer factura;
	private Integer periodo;
	private String generador;
	private Double valor;
	private Float abono;
	private ArrayList<PagosEstadoCuentaDTO> pagos;
	private ArrayList<SaldoFavorDTO> saldosFavor;
	private ArrayList<NotasCreditoDTO> notasCredito;
	
	public EstadoCuentaDeudasDTO() {
		 
	}

	public EstadoCuentaDeudasDTO(Integer idDeuda, Integer factura, Integer periodo, String generador, Double valor, Float abono,
			ArrayList<PagosEstadoCuentaDTO> pagos, ArrayList<SaldoFavorDTO> saldosFavor,
			ArrayList<NotasCreditoDTO> notasCredito) {
	 
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

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
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

	public ArrayList<PagosEstadoCuentaDTO> getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList<PagosEstadoCuentaDTO> pagos) {
		this.pagos = pagos;
	}

	public ArrayList<SaldoFavorDTO> getSaldosFavor() {
		return saldosFavor;
	}

	public void setSaldosFavor(ArrayList<SaldoFavorDTO> saldosFavor) {
		this.saldosFavor = saldosFavor;
	}

	public ArrayList<NotasCreditoDTO> getNotasCredito() {
		return notasCredito;
	}

	public void setNotasCredito(ArrayList<NotasCreditoDTO> notasCredito) {
		this.notasCredito = notasCredito;
	}

	@Override
	public String toString() {
		return "EstadoCuentaDeudasDTO [idDeuda=" + idDeuda + ", factura=" + factura + ", periodo=" + periodo
				+ ", generador=" + generador + ", valor=" + valor + ", pagos=" + pagos + ", saldosFavor=" + saldosFavor
				+ ", notasCredito=" + notasCredito + "]";
	}

	public Float getAbono() {
		return abono;
	}

	public void setAbono(Float abono) {
		this.abono = abono;
	}
	
	
	
	
}
