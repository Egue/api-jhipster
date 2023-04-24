package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "contratos")
@Entity
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Long id;

    @Column(name = "numero_sistema")
    private Long numeroSistema;

    private String grupo;

    private Long estado;

    private String fisico;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_tecnologia")
    private Long idTecnologia;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    private Long inicio;

    private Long fin;

    private Long duracion;

    private String nota;

    @Column(name = "id_tarifa")
    private Long idTarifa;

    @Column(name = "id_tarifa_promo")
    private Long idTarifaPromo;

    @Column(name = "limite_promo")
    private Long limitePromo;

    private Long prorrateo;

    @Column(name = "puntos_add")
    private String puntosAdd;

    @Column(name = "id_vendedor")
    private Long idVendedor;

    @Column(name = "id_registra")
    private Long idRegistra;

    @Column(name = "id_usuario_aprueba")
    private Long idUsuarioAprueba;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_direccion_servicio")
    private Long idDireccionServicio;

    @Column(name = "id_direccion_factura")
    private Long idDireccionFactura;

    //private String marca;

    @Column(name = "otr_imp")
    private Long otrImp;

    @Column(name = "saldo_favor")
    private Float saldoFavor;

    @Column(name = "id_tarifa_instalacion")
    private Long idTarifaInstalacion;

    @Column(name = "id_zona")
    private Long idZona;

    private String sumamovil;

    private String precintos;

    @Column(name = "dual_c")
    private Long dualC;

    private Long scord;

    @Column(name = "id_estacion")
    private Long idEstacion;

    @Column(name = "id_ap_master")
    private Long idApMaster;

    private Long combo;

    @Column(name = "id_combo")
    private Long idCombo;

    @Column(name = "ip_publica")
    private Long ipPublica;

    /*@Column(name = "ultimo_pago")
    private String ultimoPago;*/

    @Column(name = "ultimo_estado")
    private String ultimoEstado;

    @Column(name = "preferencia_factura")
    private Long preferenciaFactura;

    private Long estrato;

    private String venta;

    @Column(name = "priorizar_deudas")
    private Long priorizarDeudas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroSistema() {
        return numeroSistema;
    }

    public void setNumeroSistema(Long numeroSistema) {
        this.numeroSistema = numeroSistema;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getFisico() {
        return fisico;
    }

    public void setFisico(String fisico) {
        this.fisico = fisico;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdTecnologia() {
        return idTecnologia;
    }

    public void setIdTecnologia(Long idTecnologia) {
        this.idTecnologia = idTecnologia;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getInicio() {
        return inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Long getFin() {
        return fin;
    }

    public void setFin(Long fin) {
        this.fin = fin;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Long getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Long getIdTarifaPromo() {
        return idTarifaPromo;
    }

    public void setIdTarifaPromo(Long idTarifaPromo) {
        this.idTarifaPromo = idTarifaPromo;
    }

    public Long getLimitePromo() {
        return limitePromo;
    }

    public void setLimitePromo(Long limitePromo) {
        this.limitePromo = limitePromo;
    }

    public Long getProrrateo() {
        return prorrateo;
    }

    public void setProrrateo(Long prorrateo) {
        this.prorrateo = prorrateo;
    }

    public String getPuntosAdd() {
        return puntosAdd;
    }

    public void setPuntosAdd(String puntosAdd) {
        this.puntosAdd = puntosAdd;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Long getIdRegistra() {
        return idRegistra;
    }

    public void setIdRegistra(Long idRegistra) {
        this.idRegistra = idRegistra;
    }

    public Long getIdUsuarioAprueba() {
        return idUsuarioAprueba;
    }

    public void setIdUsuarioAprueba(Long idUsuarioAprueba) {
        this.idUsuarioAprueba = idUsuarioAprueba;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdDireccionServicio() {
        return idDireccionServicio;
    }

    public void setIdDireccionServicio(Long idDireccionServicio) {
        this.idDireccionServicio = idDireccionServicio;
    }

    public Long getIdDireccionFactura() {
        return idDireccionFactura;
    }

    public void setIdDireccionFactura(Long idDireccionFactura) {
        this.idDireccionFactura = idDireccionFactura;
    }

    /*public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }*/

    public Long getOtrImp() {
        return otrImp;
    }

    public void setOtrImp(Long otrImp) {
        this.otrImp = otrImp;
    }

    public Float getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(Float saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public Long getIdTarifaInstalacion() {
        return idTarifaInstalacion;
    }

    public void setIdTarifaInstalacion(Long idTarifaInstalacion) {
        this.idTarifaInstalacion = idTarifaInstalacion;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public String getSumamovil() {
        return sumamovil;
    }

    public void setSumamovil(String sumamovil) {
        this.sumamovil = sumamovil;
    }

    public String getPrecintos() {
        return precintos;
    }

    public void setPrecintos(String precintos) {
        this.precintos = precintos;
    }

    public Long getDualC() {
        return dualC;
    }

    public void setDualC(Long dualC) {
        this.dualC = dualC;
    }

    public Long getScord() {
        return scord;
    }

    public void setScord(Long scord) {
        this.scord = scord;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getIdApMaster() {
        return idApMaster;
    }

    public void setIdApMaster(Long idApMaster) {
        this.idApMaster = idApMaster;
    }

    public Long getCombo() {
        return combo;
    }

    public void setCombo(Long combo) {
        this.combo = combo;
    }

    public Long getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Long idCombo) {
        this.idCombo = idCombo;
    }

    public Long getIpPublica() {
        return ipPublica;
    }

    public void setIpPublica(Long ipPublica) {
        this.ipPublica = ipPublica;
    }

    /*public String getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(String ultimoPago) {
        this.ultimoPago = ultimoPago;
    }*/

    public String getUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(String ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }

    public Long getPreferenciaFactura() {
        return preferenciaFactura;
    }

    public void setPreferenciaFactura(Long preferenciaFactura) {
        this.preferenciaFactura = preferenciaFactura;
    }

    public Long getEstrato() {
        return estrato;
    }

    public void setEstrato(Long estrato) {
        this.estrato = estrato;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public Long getPriorizarDeudas() {
        return priorizarDeudas;
    }

    public void setPriorizarDeudas(Long priorizarDeudas) {
        this.priorizarDeudas = priorizarDeudas;
    }
}
