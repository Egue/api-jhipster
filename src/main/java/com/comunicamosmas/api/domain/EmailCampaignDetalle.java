package com.comunicamosmas.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_campaing_detalle")
public class EmailCampaignDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_email_campaing")
	private Integer idEmailCampaign;
	
	@Column(name="factura")
	private String factura;
	
	@Column(name="id_cliente")
	private Integer idCliente;
	
	private String email;
	
	@Column(name="respuesta_mail_relay")
	private String respuestaMailRelay;
	
	@Column(name="id_servicio")
	private Integer idServicio;
	
	private Integer procesado;

	private String origen;

	@Column(name="name_document")
	private String nameDocument;

	public String getNameDocument() {
		return nameDocument;
	}

	public void setNameDocument(String nameDocument) {
		this.nameDocument = nameDocument;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEmailCampaign() {
		return idEmailCampaign;
	}

	public void setIdEmailCampaign(Integer idEmailCampaign) {
		this.idEmailCampaign = idEmailCampaign;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRespuestaMailRelay() {
		return respuestaMailRelay;
	}

	public void setRespuestaMailRelay(String respuestaMailRelay) {
		this.respuestaMailRelay = respuestaMailRelay;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public Integer getProcesado() {
		return procesado;
	}

	public void setProcesado(Integer procesado) {
		this.procesado = procesado;
	}
 
	
	
	

}
