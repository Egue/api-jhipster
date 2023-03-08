package com.comunicamosmas.api.service.dto;

import java.math.BigInteger;

public class MigracionTarifasInfoDTO {

	private Integer idContrato;
	private String nombreCliente;
	private BigInteger documento;
	private String nombreTarifaPromo;
	private Integer valorTarifaPromo;
	private Integer velocidadTarifaPromo;
	private String nombreTarifaGeneral;
	private Integer valorTarifaGeneral;
	private Integer velocidadTarifaGeneral;
	private String nombreTecnologia;
	private Integer idServicio;
	private String TipoCliente;
	private Integer idTecnolgia;
	private Integer idEmpresa;
	private Integer idCiudad;
	private Integer idCliente;
	private String lugar;
	private Integer idTarifaPromo;
	private Integer idTarifa;
	public MigracionTarifasInfoDTO() {
		 
	}
	public MigracionTarifasInfoDTO(Integer idContrato, String nombreCliente, BigInteger documento,
			String nombreTarifaPromo, Integer valorTarifaPromo, Integer velocidadTarifaPromo,
			String nombreTarifaGeneral, Integer valorTarifaGeneral, Integer velocidadTarifaGeneral,
			String nombreTecnologia, Integer idServicio, String tipoCliente, Integer idTecnolgia, Integer idEmpresa,
			Integer idCiudad, Integer idCliente, String lugar, Integer idTarifaPromo, Integer idTarifa) {
		super();
		this.idContrato = idContrato;
		this.nombreCliente = nombreCliente;
		this.documento = documento;
		this.nombreTarifaPromo = nombreTarifaPromo;
		this.valorTarifaPromo = valorTarifaPromo;
		this.velocidadTarifaPromo = velocidadTarifaPromo;
		this.nombreTarifaGeneral = nombreTarifaGeneral;
		this.valorTarifaGeneral = valorTarifaGeneral;
		this.velocidadTarifaGeneral = velocidadTarifaGeneral;
		this.nombreTecnologia = nombreTecnologia;
		this.idServicio = idServicio;
		TipoCliente = tipoCliente;
		this.idTecnolgia = idTecnolgia;
		this.idEmpresa = idEmpresa;
		this.idCiudad = idCiudad;
		this.idCliente = idCliente;
		this.lugar = lugar;
		this.idTarifaPromo = idTarifaPromo;
		this.idTarifa = idTarifa;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
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
	public String getNombreTarifaPromo() {
		return nombreTarifaPromo;
	}
	public void setNombreTarifaPromo(String nombreTarifaPromo) {
		this.nombreTarifaPromo = nombreTarifaPromo;
	}
	public Integer getValorTarifaPromo() {
		return valorTarifaPromo;
	}
	public void setValorTarifaPromo(Integer valorTarifaPromo) {
		this.valorTarifaPromo = valorTarifaPromo;
	}
	public Integer getVelocidadTarifaPromo() {
		return velocidadTarifaPromo;
	}
	public void setVelocidadTarifaPromo(Integer velocidadTarifaPromo) {
		this.velocidadTarifaPromo = velocidadTarifaPromo;
	}
	public String getNombreTarifaGeneral() {
		return nombreTarifaGeneral;
	}
	public void setNombreTarifaGeneral(String nombreTarifaGeneral) {
		this.nombreTarifaGeneral = nombreTarifaGeneral;
	}
	public Integer getValorTarifaGeneral() {
		return valorTarifaGeneral;
	}
	public void setValorTarifaGeneral(Integer valorTarifaGeneral) {
		this.valorTarifaGeneral = valorTarifaGeneral;
	}
	public Integer getVelocidadTarifaGeneral() {
		return velocidadTarifaGeneral;
	}
	public void setVelocidadTarifaGeneral(Integer velocidadTarifaGeneral) {
		this.velocidadTarifaGeneral = velocidadTarifaGeneral;
	}
	public String getNombreTecnologia() {
		return nombreTecnologia;
	}
	public void setNombreTecnologia(String nombreTecnologia) {
		this.nombreTecnologia = nombreTecnologia;
	}
	public Integer getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
	public String getTipoCliente() {
		return TipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		TipoCliente = tipoCliente;
	}
	public Integer getIdTecnolgia() {
		return idTecnolgia;
	}
	public void setIdTecnolgia(Integer idTecnolgia) {
		this.idTecnolgia = idTecnolgia;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public Integer getIdTarifaPromo() {
		return idTarifaPromo;
	}
	public void setIdTarifaPromo(Integer idTarifaPromo) {
		this.idTarifaPromo = idTarifaPromo;
	}
	public Integer getIdTarifa() {
		return idTarifa;
	}
	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}
	@Override
	public String toString() {
		return "MigracionTarifasInfoDTO [idContrato=" + idContrato + ", nombreCliente=" + nombreCliente + ", documento="
				+ documento + ", nombreTarifaPromo=" + nombreTarifaPromo + ", valorTarifaPromo=" + valorTarifaPromo
				+ ", velocidadTarifaPromo=" + velocidadTarifaPromo + ", nombreTarifaGeneral=" + nombreTarifaGeneral
				+ ", valorTarifaGeneral=" + valorTarifaGeneral + ", velocidadTarifaGeneral=" + velocidadTarifaGeneral
				+ ", nombreTecnologia=" + nombreTecnologia + ", idServicio=" + idServicio + ", TipoCliente="
				+ TipoCliente + ", idTecnolgia=" + idTecnolgia + ", idEmpresa=" + idEmpresa + ", idCiudad=" + idCiudad
				+ ", idCliente=" + idCliente + ", lugar=" + lugar + ", idTarifaPromo=" + idTarifaPromo + ", idTarifa="
				+ idTarifa + "]";
	}
	
	 
	 
	
	
	
}
