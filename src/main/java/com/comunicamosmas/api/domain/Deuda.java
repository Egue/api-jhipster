package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "deudas")
@Entity
public class Deuda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deuda")
    private Long id;

    @Column(name = "id_cliente")
    private String idCliente;

    private String refiere;

    private Long estado;

    private Long factura;

    private String servicio;

    @Column(name = "id_tarifa")
    private Long idTarifa;

    @Column(name = "valor_base")
    private Double valorBase;

    @Column(name = "valor_iva")
    private Double valorIva;

    @Column(name = "valor_reteiva")
    private Double valorReteiva;

    @Column(name = "valor_refuente")
    private Double valorRefuente;

    @Column(name = "valor_reteica")
    private Double valorReteica;

    @Column(name = "valor_bomberil")
    private Double valorBomberil;

    @Column(name = "valor_otrosimp")
    private Double valorOtrosImp;

    @Column(name = "valor_parcial")
    private Double valorParcial;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "valor_intereses")
    private Double valorIntereses;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    private String marca;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "mes_servicio")
    private Long mesServicio;

    @Column(name = "fechaf_genera")
    private Long fechafGenera;

    @Column(name = "fechaf_corte")
    private Long fechafCorte;

    @Column(name = "fecha_limite")
    private Long fechaLimite;

    private Long repetitivo;

    private String pucc;

    private String niff;

    private Long instalacion;

    private Long reconexion;

    private Long materiales;

    private Long traslado;

    private Long otros;

    @Column(name = "concepto_aux")
    private String conceptoAux;

    private Long generador;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "facturado_fecha")
    private Long facturadoFecha;

    @Column(name = "facturado_hora")
    private String facturadoHora;

    @Column(name = "facturado_id_resolucion")
    private Long facturadoIdResolucion;

    private String pasarela;

    @Column(name = "fac_electronica")
    private String facElectronica;

    @Column(name = "resultado_factura_electronica")
    private String resultadoFacturaElectronica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getRefiere() {
        return refiere;
    }

    public void setRefiere(String refiere) {
        this.refiere = refiere;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Long getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
    }

    public Double getValorReteiva() {
        return valorReteiva;
    }

    public void setValorReteiva(Double valorReteiva) {
        this.valorReteiva = valorReteiva;
    }

    public Double getValorRefuente() {
        return valorRefuente;
    }

    public void setValorRefuente(Double valorRefuente) {
        this.valorRefuente = valorRefuente;
    }

    public Double getValorReteica() {
        return valorReteica;
    }

    public void setValorReteica(Double valorReteica) {
        this.valorReteica = valorReteica;
    }

    public Double getValorBomberil() {
        return valorBomberil;
    }

    public void setValorBomberil(Double valorBomberil) {
        this.valorBomberil = valorBomberil;
    }

    public Double getValorOtrosImp() {
        return valorOtrosImp;
    }

    public void setValorOtrosImp(Double valorOtrosImp) {
        this.valorOtrosImp = valorOtrosImp;
    }

    public Double getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(Double valorParcial) {
        this.valorParcial = valorParcial;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorIntereses() {
        return valorIntereses;
    }

    public void setValorIntereses(Double valorIntereses) {
        this.valorIntereses = valorIntereses;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getMesServicio() {
        return mesServicio;
    }

    public void setMesServicio(Long mesServicio) {
        this.mesServicio = mesServicio;
    }

    public Long getFechafGenera() {
        return fechafGenera;
    }

    public void setFechafGenera(Long fechafGenera) {
        this.fechafGenera = fechafGenera;
    }

    public Long getFechafCorte() {
        return fechafCorte;
    }

    public void setFechafCorte(Long fechafCorte) {
        this.fechafCorte = fechafCorte;
    }

    public Long getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Long fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Long getRepetitivo() {
        return repetitivo;
    }

    public void setRepetitivo(Long repetitivo) {
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

    public Long getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Long instalacion) {
        this.instalacion = instalacion;
    }

    public Long getReconexion() {
        return reconexion;
    }

    public void setReconexion(Long reconexion) {
        this.reconexion = reconexion;
    }

    public Long getMateriales() {
        return materiales;
    }

    public void setMateriales(Long materiales) {
        this.materiales = materiales;
    }

    public Long getTraslado() {
        return traslado;
    }

    public void setTraslado(Long traslado) {
        this.traslado = traslado;
    }

    public Long getOtros() {
        return otros;
    }

    public void setOtros(Long otros) {
        this.otros = otros;
    }

    public String getConceptoAux() {
        return conceptoAux;
    }

    public void setConceptoAux(String conceptoAux) {
        this.conceptoAux = conceptoAux;
    }

    public Long getGenerador() {
        return generador;
    }

    public void setGenerador(Long generador) {
        this.generador = generador;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getFacturadoFecha() {
        return facturadoFecha;
    }

    public void setFacturadoFecha(Long facturadoFecha) {
        this.facturadoFecha = facturadoFecha;
    }

    public String getFacturadoHora() {
        return facturadoHora;
    }

    public void setFacturadoHora(String facturadoHora) {
        this.facturadoHora = facturadoHora;
    }

    public Long getFacturadoIdResolucion() {
        return facturadoIdResolucion;
    }

    public void setFacturadoIdResolucion(Long facturadoIdResolucion) {
        this.facturadoIdResolucion = facturadoIdResolucion;
    }

    public String getPasarela() {
        return pasarela;
    }

    public void setPasarela(String pasarela) {
        this.pasarela = pasarela;
    }

    public String getFacElectronica() {
        return facElectronica;
    }

    public void setFacElectronica(String facElectronica) {
        this.facElectronica = facElectronica;
    }

    public String getResultadoFacturaElectronica() {
        return resultadoFacturaElectronica;
    }

    public void setResultadoFacturaElectronica(String resultadoFacturaElectronica) {
        this.resultadoFacturaElectronica = resultadoFacturaElectronica;
    }
}
