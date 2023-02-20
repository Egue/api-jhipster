package com.comunicamosmas.api.service.dto;

public class SegmentoWithPoolDTO {
	private Long id;
	private Long idEstacion;
	private String segmento;
	private String estado;
	private String nombrePool;
	
	public SegmentoWithPoolDTO() {
	 
	}

	public SegmentoWithPoolDTO(Long id, Long idEstacion, String segmento, String estado, String nombrePool) {
		 
		this.id = id;
		this.idEstacion = idEstacion;
		this.segmento = segmento;
		this.estado = estado;
		this.nombrePool = nombrePool;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombrePool() {
		return nombrePool;
	}

	public void setNombrePool(String nombrePool) {
		this.nombrePool = nombrePool;
	}

	@Override
	public String toString() {
		return "SegmentoWithPoolDTO [id=" + id + ", idEstacion=" + idEstacion + ", segmento=" + segmento + ", estado="
				+ estado + ", nombrePool=" + nombrePool + "]";
	} 
	
	
	
}
