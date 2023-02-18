package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "empresas")
@Entity
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    private Long estado;

    private String telefonos;

    private Long nit;

    private Long dv;

    private String logo;

    private String direccion;

    private String ciudad;

    @Column(name = "factura_a_numero_inicio")
    private Long facturaANumeroInicio;

    @Column(name = "factura_a_numero_final")
    private Long facturaANumeroFinal;

    @Column(name = "factura_a_resolucion")
    private String facturaAResolucion;

    @Column(name = "factura_a_fecha_inicio")
    private Long facturaAFechaInicio;

    @Column(name = "factura_a_fecha_vence")
    private Long facturaAFechaVence;

    @Column(name = "factura_a_prefijo")
    private String facturaAPrefijo;

    @Column(name = "factura_b_numero_inicio")
    private Long facturaBNumeroInicio;

    @Column(name = "factura_b_numero_final")
    private Long facturaBNumeroFinal;

    @Column(name = "factura_b_resolucion")
    private String facturaBResolucion;

    @Column(name = "factura_b_fecha_inicio")
    private Long facturaBFechaInicio;

    @Column(name = "factura_b_fecha_vence")
    private Long facturaBFechaVence;

    @Column(name = "factura_b_prefijo")
    private String facturaBPrefijo;

    private String marca;

    @Column(name = "estado_cuenta")
    private Long estadoCuenta;

    @Column(name = "id_servicio_servicio")
    private Long idServicioServicio;

    @Column(name = "id_servicio_instalacion")
    private Long idServicioInstalacion;

    @Column(name = "id_servicio_reconexion")
    private Long idServicioReconexion;

    private String regimen;

    @Column(name = "anexo_factura")
    private String anexoFactura;

    private String pdfa;

    private String pdfb;

    private String pdfc;

    private String pdfd;

    @Column(name = "factura_sms")
    private Long facturaSms;

    @Column(name = "factura_electronica")
    private Long facturaElectronica;

    @Column(name = "id_resolucion_activa")
    private Long idResolucionActiva;

    @Column(name = "logo_publico")
    private String logoPublico;

    private String web;

    @Column(name = "fondo_factura")
    private String fondoFactura;

    @Column(name = "fondo_factura_b")
    private String fondoFacturaB;

    @Column(name = "tc_api")
    private String tcApi;

    @Column(name = "muestra_fisicos")
    private Long muestra_fisicos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public Long getDv() {
        return dv;
    }

    public void setDv(Long dv) {
        this.dv = dv;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getFacturaANumeroInicio() {
        return facturaANumeroInicio;
    }

    public void setFacturaANumeroInicio(Long facturaANumeroInicio) {
        this.facturaANumeroInicio = facturaANumeroInicio;
    }

    public Long getFacturaANumeroFinal() {
        return facturaANumeroFinal;
    }

    public void setFacturaANumeroFinal(Long facturaANumeroFinal) {
        this.facturaANumeroFinal = facturaANumeroFinal;
    }

    public String getFacturaAResolucion() {
        return facturaAResolucion;
    }

    public void setFacturaAResolucion(String facturaAResolucion) {
        this.facturaAResolucion = facturaAResolucion;
    }

    public Long getFacturaAFechaInicio() {
        return facturaAFechaInicio;
    }

    public void setFacturaAFechaInicio(Long facturaAFechaInicio) {
        this.facturaAFechaInicio = facturaAFechaInicio;
    }

    public Long getFacturaAFechaVence() {
        return facturaAFechaVence;
    }

    public void setFacturaAFechaVence(Long facturaAFechaVence) {
        this.facturaAFechaVence = facturaAFechaVence;
    }

    public String getFacturaAPrefijo() {
        return facturaAPrefijo;
    }

    public void setFacturaAPrefijo(String facturaAPrefijo) {
        this.facturaAPrefijo = facturaAPrefijo;
    }

    public Long getFacturaBNumeroInicio() {
        return facturaBNumeroInicio;
    }

    public void setFacturaBNumeroInicio(Long facturaBNumeroInicio) {
        this.facturaBNumeroInicio = facturaBNumeroInicio;
    }

    public Long getFacturaBNumeroFinal() {
        return facturaBNumeroFinal;
    }

    public void setFacturaBNumeroFinal(Long facturaBNumeroFinal) {
        this.facturaBNumeroFinal = facturaBNumeroFinal;
    }

    public String getFacturaBResolucion() {
        return facturaBResolucion;
    }

    public void setFacturaBResolucion(String facturaBResolucion) {
        this.facturaBResolucion = facturaBResolucion;
    }

    public Long getFacturaBFechaInicio() {
        return facturaBFechaInicio;
    }

    public void setFacturaBFechaInicio(Long facturaBFechaInicio) {
        this.facturaBFechaInicio = facturaBFechaInicio;
    }

    public Long getFacturaBFechaVence() {
        return facturaBFechaVence;
    }

    public void setFacturaBFechaVence(Long facturaBFechaVence) {
        this.facturaBFechaVence = facturaBFechaVence;
    }

    public String getFacturaBPrefijo() {
        return facturaBPrefijo;
    }

    public void setFacturaBPrefijo(String facturaBPrefijo) {
        this.facturaBPrefijo = facturaBPrefijo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Long estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Long getIdServicioServicio() {
        return idServicioServicio;
    }

    public void setIdServicioServicio(Long idServicioServicio) {
        this.idServicioServicio = idServicioServicio;
    }

    public Long getIdServicioInstalacion() {
        return idServicioInstalacion;
    }

    public void setIdServicioInstalacion(Long idServicioInstalacion) {
        this.idServicioInstalacion = idServicioInstalacion;
    }

    public Long getIdServicioReconexion() {
        return idServicioReconexion;
    }

    public void setIdServicioReconexion(Long idServicioReconexion) {
        this.idServicioReconexion = idServicioReconexion;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getAnexoFactura() {
        return anexoFactura;
    }

    public void setAnexoFactura(String anexoFactura) {
        this.anexoFactura = anexoFactura;
    }

    public String getPdfa() {
        return pdfa;
    }

    public void setPdfa(String pdfa) {
        this.pdfa = pdfa;
    }

    public String getPdfb() {
        return pdfb;
    }

    public void setPdfb(String pdfb) {
        this.pdfb = pdfb;
    }

    public String getPdfc() {
        return pdfc;
    }

    public void setPdfc(String pdfc) {
        this.pdfc = pdfc;
    }

    public String getPdfd() {
        return pdfd;
    }

    public void setPdfd(String pdfd) {
        this.pdfd = pdfd;
    }

    public Long getFacturaSms() {
        return facturaSms;
    }

    public void setFacturaSms(Long facturaSms) {
        this.facturaSms = facturaSms;
    }

    public Long getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(Long facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    public Long getIdResolucionActiva() {
        return idResolucionActiva;
    }

    public void setIdResolucionActiva(Long idResolucionActiva) {
        this.idResolucionActiva = idResolucionActiva;
    }

    public String getLogoPublico() {
        return logoPublico;
    }

    public void setLogoPublico(String logoPublico) {
        this.logoPublico = logoPublico;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFondoFactura() {
        return fondoFactura;
    }

    public void setFondoFactura(String fondoFactura) {
        this.fondoFactura = fondoFactura;
    }

    public String getFondoFacturaB() {
        return fondoFacturaB;
    }

    public void setFondoFacturaB(String fondoFacturaB) {
        this.fondoFacturaB = fondoFacturaB;
    }

    public String getTcApi() {
        return tcApi;
    }

    public void setTcApi(String tcApi) {
        this.tcApi = tcApi;
    }

    public Long getMuestra_fisicos() {
        return muestra_fisicos;
    }

    public void setMuestra_fisicos(Long muestra_fisicos) {
        this.muestra_fisicos = muestra_fisicos;
    }
}
