package com.comunicamosmas.api.service.dto;

public class MigracionTarifaFindContratoDTO {
	private Integer idMigracion;
	private Integer idContrato;
	private Integer idGeneralAntes;
	private Integer idPromoAntes;
	private Integer idGeneralNew;
	private Integer idPromoNew;
	private Integer idServicio;
	private Integer idEmpresa;
	private String tarifaAnteriorGeneral;
	private String tarifaAnteriorPromo;
	private  String  tarifaNewGeneral;
	private String tarifaNewPro;
	private String  usuario;
	private String justificacion;
	public MigracionTarifaFindContratoDTO() {
		 
	}
	public MigracionTarifaFindContratoDTO(Integer idMigracion, Integer idContrato, Integer idGeneralAntes,
			Integer idPromoAntes, Integer idGeneralNew, Integer idPromoNew, Integer idServicio, Integer idEmpresa,
			String tarifaAnteriorGeneral, String tarifaAnteriorPromo, String tarifaNewGeneral, String tarifaNewPro,
			String usuario, String justificacion) {
		 
		this.idMigracion = idMigracion;
		this.idContrato = idContrato;
		this.idGeneralAntes = idGeneralAntes;
		this.idPromoAntes = idPromoAntes;
		this.idGeneralNew = idGeneralNew;
		this.idPromoNew = idPromoNew;
		this.idServicio = idServicio;
		this.idEmpresa = idEmpresa;
		this.tarifaAnteriorGeneral = tarifaAnteriorGeneral;
		this.tarifaAnteriorPromo = tarifaAnteriorPromo;
		this.tarifaNewGeneral = tarifaNewGeneral;
		this.tarifaNewPro = tarifaNewPro;
		this.usuario = usuario;
		this.justificacion = justificacion;
	}
	public Integer getIdMigracion() {
		return idMigracion;
	}
	public void setIdMigracion(Integer idMigracion) {
		this.idMigracion = idMigracion;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdGeneralAntes() {
		return idGeneralAntes;
	}
	public void setIdGeneralAntes(Integer idGeneralAntes) {
		this.idGeneralAntes = idGeneralAntes;
	}
	public Integer getIdPromoAntes() {
		return idPromoAntes;
	}
	public void setIdPromoAntes(Integer idPromoAntes) {
		this.idPromoAntes = idPromoAntes;
	}
	public Integer getIdGeneralNew() {
		return idGeneralNew;
	}
	public void setIdGeneralNew(Integer idGeneralNew) {
		this.idGeneralNew = idGeneralNew;
	}
	public Integer getIdPromoNew() {
		return idPromoNew;
	}
	public void setIdPromoNew(Integer idPromoNew) {
		this.idPromoNew = idPromoNew;
	}
	public Integer getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getTarifaAnteriorGeneral() {
		return tarifaAnteriorGeneral;
	}
	public void setTarifaAnteriorGeneral(String tarifaAnteriorGeneral) {
		this.tarifaAnteriorGeneral = tarifaAnteriorGeneral;
	}
	public String getTarifaAnteriorPromo() {
		return tarifaAnteriorPromo;
	}
	public void setTarifaAnteriorPromo(String tarifaAnteriorPromo) {
		this.tarifaAnteriorPromo = tarifaAnteriorPromo;
	}
	public String getTarifaNewGeneral() {
		return tarifaNewGeneral;
	}
	public void setTarifaNewGeneral(String tarifaNewGeneral) {
		this.tarifaNewGeneral = tarifaNewGeneral;
	}
	public String getTarifaNewPro() {
		return tarifaNewPro;
	}
	public void setTarifaNewPro(String tarifaNewPro) {
		this.tarifaNewPro = tarifaNewPro;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	@Override
	public String toString() {
		return "MigracionTarifaFindContratoDTO [idMigracion=" + idMigracion + ", idContrato=" + idContrato
				+ ", idGeneralAntes=" + idGeneralAntes + ", idPromoAntes=" + idPromoAntes + ", idGeneralNew="
				+ idGeneralNew + ", idPromoNew=" + idPromoNew + ", idServicio=" + idServicio + ", idEmpresa="
				+ idEmpresa + ", tarifaAnteriorGeneral=" + tarifaAnteriorGeneral + ", tarifaAnteriorPromo="
				+ tarifaAnteriorPromo + ", tarifaNewGeneral=" + tarifaNewGeneral + ", tarifaNewPro=" + tarifaNewPro
				+ ", usuario=" + usuario + ", justificacion=" + justificacion + "]";
	}
	
	
	
	

}
