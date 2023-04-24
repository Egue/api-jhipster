package com.comunicamosmas.api.service.dto;

import java.util.Date;

public class SupergirosPagosDTO {
	public int id;
    public String fecha_recaudo;
    public String hora_recaudo;
    public String id_transaccion;
    public String cc_asesor;
    public String nombre_asesor;
    public String codigo_pto_vta;
    public String nombre_pto_vta;
    public String nombre_convenio;
    public String numero_referencia;
    public int valor_total;
    public String cc_cliente;
    public String nombre_cliente;
    public int estado_descargue;
    public int reversado;
    public Object comentario_reversado;
    public Object fecha_reversado;
    public Date created_at;
    public Date updated_at;
	public SupergirosPagosDTO() {
	 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFecha_recaudo() {
		return fecha_recaudo;
	}
	public void setFecha_recaudo(String fecha_recaudo) {
		this.fecha_recaudo = fecha_recaudo;
	}
	public String getHora_recaudo() {
		return hora_recaudo;
	}
	public void setHora_recaudo(String hora_recaudo) {
		this.hora_recaudo = hora_recaudo;
	}
	public String getId_transaccion() {
		return id_transaccion;
	}
	public void setId_transaccion(String id_transaccion) {
		this.id_transaccion = id_transaccion;
	}
	public String getCc_asesor() {
		return cc_asesor;
	}
	public void setCc_asesor(String cc_asesor) {
		this.cc_asesor = cc_asesor;
	}
	public String getNombre_asesor() {
		return nombre_asesor;
	}
	public void setNombre_asesor(String nombre_asesor) {
		this.nombre_asesor = nombre_asesor;
	}
	public String getCodigo_pto_vta() {
		return codigo_pto_vta;
	}
	public void setCodigo_pto_vta(String codigo_pto_vta) {
		this.codigo_pto_vta = codigo_pto_vta;
	}
	public String getNombre_pto_vta() {
		return nombre_pto_vta;
	}
	public void setNombre_pto_vta(String nombre_pto_vta) {
		this.nombre_pto_vta = nombre_pto_vta;
	}
	public String getNombre_convenio() {
		return nombre_convenio;
	}
	public void setNombre_convenio(String nombre_convenio) {
		this.nombre_convenio = nombre_convenio;
	}
	public String getNumero_referencia() {
		return numero_referencia;
	}
	public void setNumero_referencia(String numero_referencia) {
		this.numero_referencia = numero_referencia;
	}
	public int getValor_total() {
		return valor_total;
	}
	public void setValor_total(int valor_total) {
		this.valor_total = valor_total;
	}
	public String getCc_cliente() {
		return cc_cliente;
	}
	public void setCc_cliente(String cc_cliente) {
		this.cc_cliente = cc_cliente;
	}
	public String getNombre_cliente() {
		return nombre_cliente;
	}
	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}
	public int getEstado_descargue() {
		return estado_descargue;
	}
	public void setEstado_descargue(int estado_descargue) {
		this.estado_descargue = estado_descargue;
	}
	public int getReversado() {
		return reversado;
	}
	public void setReversado(int reversado) {
		this.reversado = reversado;
	}
	public Object getComentario_reversado() {
		return comentario_reversado;
	}
	public void setComentario_reversado(Object comentario_reversado) {
		this.comentario_reversado = comentario_reversado;
	}
	public Object getFecha_reversado() {
		return fecha_reversado;
	}
	public void setFecha_reversado(Object fecha_reversado) {
		this.fecha_reversado = fecha_reversado;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
    
    
}
