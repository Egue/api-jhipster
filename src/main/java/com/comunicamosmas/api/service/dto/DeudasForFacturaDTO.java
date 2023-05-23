package com.comunicamosmas.api.service.dto;

import java.sql.Timestamp;

import javax.persistence.Column;

public class DeudasForFacturaDTO {
	
	 
    private Integer id_deuda;

     
    private Integer id_cliente;

    private String refiere;

    private Integer estado;

    private Integer factura;

    private String servicio;
 
    private Integer id_tarifa;
 
    private Double valor_base;
 
    private Float valor_iva;
 
    private Float valor_reteiva;
  
    private Float valor_refuente;
 
    private Float valor_reteica;
 
    private Float valor_bomberil;
 
    private Float valor_otrosimp;
 
    private Float valor_parcial;
 
    private Double valor_total;
 
    private Integer valor_intereses;
 
    private Integer id_contrato;
 
    private Integer id_servicio;
 
    private Integer id_empresa;

    private Timestamp marca;
 
    private Integer id_ciudad;
 
    private Integer mes_servicio;
 
    private Integer fechaf_genera;
 
    private Integer fechaf_corte;
 
    private Integer fecha_limite;

    private Integer repetitivo;

    private String pucc;

    private String niff;

    private Integer instalacion;

    private Integer reconexion;

    private Integer materiales;

    private Integer traslado;

    private Integer otros;
     
    private String concepto_aux;

    private Integer generador;
 
    private Integer id_usuario;
 
    private Integer facturado_fecha;
 
    private String facturado_hora;
 
    private Integer facturado_id_resolucion;

    private String pasarela;
 
    private String fac_electronica;
 
    private String resultado_factura_electronica;
    

	private String nombre;
    
    private String rango_inicio;
    private String rango_final;
    private String prefijo;
    private String num_resolucion;
    private Integer vigencia;
    private Integer fecha_resolucion;

	public DeudasForFacturaDTO() {
		 
	}

	public Integer getId_deuda() {
		return id_deuda;
	}

	public void setId_deuda(Integer id_deuda) {
		this.id_deuda = id_deuda;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getRefiere() {
		return refiere;
	}

	public void setRefiere(String refiere) {
		this.refiere = refiere;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getFactura() {
		return factura;
	}

	public void setFactura(Integer factura) {
		this.factura = factura;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public Integer getId_tarifa() {
		return id_tarifa;
	}

	public void setId_tarifa(Integer id_tarifa) {
		this.id_tarifa = id_tarifa;
	}

	public Double getValor_base() {
		return valor_base;
	}

	public void setValor_base(Double valor_base) {
		this.valor_base = valor_base;
	}

	public Float getValor_iva() {
		return valor_iva;
	}

	public void setValor_iva(Float valor_iva) {
		this.valor_iva = valor_iva;
	}

	public Float getValor_reteiva() {
		return valor_reteiva;
	}

	public void setValor_reteiva(Float valor_reteiva) {
		this.valor_reteiva = valor_reteiva;
	}

	public Float getValor_refuente() {
		return valor_refuente;
	}

	public void setValor_refuente(Float valor_refuente) {
		this.valor_refuente = valor_refuente;
	}

	public Float getValor_reteica() {
		return valor_reteica;
	}

	public void setValor_reteica(Float valor_reteica) {
		this.valor_reteica = valor_reteica;
	}

	public Float getValor_bomberil() {
		return valor_bomberil;
	}

	public void setValor_bomberil(Float valor_bomberil) {
		this.valor_bomberil = valor_bomberil;
	}

	public Float getValor_otrosimp() {
		return valor_otrosimp;
	}

	public void setValor_otrosimp(Float valor_otrosimp) {
		this.valor_otrosimp = valor_otrosimp;
	}

	public Float getValor_parcial() {
		return valor_parcial;
	}

	public void setValor_parcial(Float valor_parcial) {
		this.valor_parcial = valor_parcial;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public Integer getValor_intereses() {
		return valor_intereses;
	}

	public void setValor_intereses(Integer valor_intereses) {
		this.valor_intereses = valor_intereses;
	}

	public Integer getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(Integer id_contrato) {
		this.id_contrato = id_contrato;
	}

	public Integer getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(Integer id_servicio) {
		this.id_servicio = id_servicio;
	}

	public Integer getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(Integer id_empresa) {
		this.id_empresa = id_empresa;
	}

	public Timestamp getMarca() {
		return marca;
	}

	public void setMarca(Timestamp marca) {
		this.marca = marca;
	}

	public Integer getId_ciudad() {
		return id_ciudad;
	}

	public void setId_ciudad(Integer id_ciudad) {
		this.id_ciudad = id_ciudad;
	}

	public Integer getMes_servicio() {
		return mes_servicio;
	}

	public void setMes_servicio(Integer mes_servicio) {
		this.mes_servicio = mes_servicio;
	}

	public Integer getFechaf_genera() {
		return fechaf_genera;
	}

	public void setFechaf_genera(Integer fechaf_genera) {
		this.fechaf_genera = fechaf_genera;
	}

	public Integer getFechaf_corte() {
		return fechaf_corte;
	}

	public void setFechaf_corte(Integer fechaf_corte) {
		this.fechaf_corte = fechaf_corte;
	}

	public Integer getFecha_limite() {
		return fecha_limite;
	}

	public void setFecha_limite(Integer fecha_limite) {
		this.fecha_limite = fecha_limite;
	}

	public Integer getRepetitivo() {
		return repetitivo;
	}

	public void setRepetitivo(Integer repetitivo) {
		this.repetitivo = repetitivo;
	}

	public String getPucc() {
		return pucc;
	}

	public void setPucc(String pucc) {
		this.pucc = pucc;
	}

	public String getNiff() {
		return niff;
	}

	public void setNiff(String niff) {
		this.niff = niff;
	}

	public Integer getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(Integer instalacion) {
		this.instalacion = instalacion;
	}

	public Integer getReconexion() {
		return reconexion;
	}

	public void setReconexion(Integer reconexion) {
		this.reconexion = reconexion;
	}

	public Integer getMateriales() {
		return materiales;
	}

	public void setMateriales(Integer materiales) {
		this.materiales = materiales;
	}

	public Integer getTraslado() {
		return traslado;
	}

	public void setTraslado(Integer traslado) {
		this.traslado = traslado;
	}

	public Integer getOtros() {
		return otros;
	}

	public void setOtros(Integer otros) {
		this.otros = otros;
	}

	public String getConcepto_aux() {
		return concepto_aux;
	}

	public void setConcepto_aux(String concepto_aux) {
		this.concepto_aux = concepto_aux;
	}

	public Integer getGenerador() {
		return generador;
	}

	public void setGenerador(Integer generador) {
		this.generador = generador;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Integer getFacturado_fecha() {
		return facturado_fecha;
	}

	public void setFacturado_fecha(Integer facturado_fecha) {
		this.facturado_fecha = facturado_fecha;
	}

	public String getFacturado_hora() {
		return facturado_hora;
	}

	public void setFacturado_hora(String facturado_hora) {
		this.facturado_hora = facturado_hora;
	}

	public Integer getFacturado_id_resolucion() {
		return facturado_id_resolucion;
	}

	public void setFacturado_id_resolucion(Integer facturado_id_resolucion) {
		this.facturado_id_resolucion = facturado_id_resolucion;
	}

	public String getPasarela() {
		return pasarela;
	}

	public void setPasarela(String pasarela) {
		this.pasarela = pasarela;
	}

	public String getFac_electronica() {
		return fac_electronica;
	}

	public void setFac_electronica(String fac_electronica) {
		this.fac_electronica = fac_electronica;
	}

	public String getResultado_factura_electronica() {
		return resultado_factura_electronica;
	}

	public void setResultado_factura_electronica(String resultado_factura_electronica) {
		this.resultado_factura_electronica = resultado_factura_electronica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

    public String getRango_inicio() {
		return rango_inicio;
	}

	public void setRango_inicio(String rango_inicio) {
		this.rango_inicio = rango_inicio;
	}

	public String getRango_final() {
		return rango_final;
	}

	public void setRango_final(String rango_final) {
		this.rango_final = rango_final;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getNum_resolucion() {
		return num_resolucion;
	}

	public void setNum_resolucion(String num_resolucion) {
		this.num_resolucion = num_resolucion;
	}

	public Integer getVigencia() {
		return vigencia;
	}

	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}

	public Integer getFecha_resolucion() {
		return fecha_resolucion;
	}

	public void setFecha_resolucion(Integer fecha_resolucion) {
		this.fecha_resolucion = fecha_resolucion;
	}

	@Override
	public String toString() {
		return "DeudasForFacturaDTO [id_deuda=" + id_deuda + ", id_cliente=" + id_cliente + ", refiere=" + refiere
				+ ", estado=" + estado + ", factura=" + factura + ", servicio=" + servicio + ", id_tarifa=" + id_tarifa
				+ ", valor_base=" + valor_base + ", valor_iva=" + valor_iva + ", valor_reteiva=" + valor_reteiva
				+ ", valor_refuente=" + valor_refuente + ", valor_reteica=" + valor_reteica + ", valor_bomberil="
				+ valor_bomberil + ", valor_otrosimp=" + valor_otrosimp + ", valor_parcial=" + valor_parcial
				+ ", valor_total=" + valor_total + ", valor_intereses=" + valor_intereses + ", id_contrato="
				+ id_contrato + ", id_servicio=" + id_servicio + ", id_empresa=" + id_empresa + ", marca=" + marca
				+ ", id_ciudad=" + id_ciudad + ", mes_servicio=" + mes_servicio + ", fechaf_genera=" + fechaf_genera
				+ ", fechaf_corte=" + fechaf_corte + ", fecha_limite=" + fecha_limite + ", repetitivo=" + repetitivo
				+ ", pucc=" + pucc + ", niff=" + niff + ", instalacion=" + instalacion + ", reconexion=" + reconexion
				+ ", materiales=" + materiales + ", traslado=" + traslado + ", otros=" + otros + ", concepto_aux="
				+ concepto_aux + ", generador=" + generador + ", id_usuario=" + id_usuario + ", facturado_fecha="
				+ facturado_fecha + ", facturado_hora=" + facturado_hora + ", facturado_id_resolucion="
				+ facturado_id_resolucion + ", pasarela=" + pasarela + ", fac_electronica=" + fac_electronica
				+ ", resultado_factura_electronica=" + resultado_factura_electronica + ", nombre=" + nombre
				+ ", rango_inicio=" + rango_inicio + ", rango_final=" + rango_final + ", prefijo=" + prefijo
				+ ", num_resolucion=" + num_resolucion + ", vigencia=" + vigencia + ", fecha_resolucion="
				+ fecha_resolucion + "]";
	}
	
	
	
    
    

}
