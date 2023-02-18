package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "servicios")
@Entity
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    private String nombre;

    private Long estado;

    private Long impocomsumo;

    private String pucc;

    private String niff;

    private String puccsiniva;

    private String niffsiniva;

    @Column(name = "pucc_intereses")
    private String puccIntereses;

    @Column(name = "niff_intereses")
    private String niffIntereses;

    @Column(name = "pucc_impocomsumo")
    private String puccImpocomsumo;

    @Column(name = "niff_impocomsumo")
    private String niffimpocomsumo;

    @Column(name = "pucc_traslado")
    private String puccTraslado;

    @Column(name = "niff_translado")
    private String niffTranslado;

    @Column(name = "pucc_materiales")
    private String puccMateriales;

    @Column(name = "niff_materiales")
    private String niffMateriales;

    @Column(name = "pucc_reconexion")
    private String puccReconexion;

    @Column(name = "niff_reconexion")
    private String niffReconexion;

    @Column(name = "pucc_pagosadelantadp")
    private String puccPagosadelantadp;

    @Column(name = "niff_pagosadelantadp")
    private String niffPagosadelantadp;

    @Column(name = "pucc_instalacion")
    private String puccInstalacion;

    @Column(name = "niff_instalacion")
    private String niffInstalacion;

    @Column(name = "pucc_notasceedito	")
    private String puccNotasceedito;

    @Column(name = "niff_notasceedito")
    private String niffNotasceedito;

    @Column(name = "pucc_notasceedito_excentas")
    private String puccNotasceeditoExcentas;

    @Column(name = "niff_notasceedito_excentas")
    private String niffNotasceeditoExcentas;

    @Column(name = "pucc_otros")
    private String puccOtros;

    @Column(name = "niff_otros")
    private String niffOtros;

    private Long intereses;

    @Column(name = "iva_str_0")
    private String iva_str_0;

    @Column(name = "iva_str_1")
    private String ivaStr1;

    @Column(name = "iva_str_2")
    private String ivaStr2;

    @Column(name = "iva_str_3")
    private String ivaStr3;

    @Column(name = "iva_str_4")
    private String ivaStr4;

    @Column(name = "iva_str_5")
    private String ivaStr5;

    @Column(name = "iva_str_6")
    private String ivaStr6;

    @Column(name = "iva_str_7")
    private String ivaStr7;

    @Column(name = "iva_str_8")
    private String ivaStr8;

    @Column(name = "formato_orden")
    private String formatoOrden;

    private String valoruvt;

    private String reteiva;

    private String retefuente;

    private String reteica;

    private String bomberil;

    private Long limitepago;

    private Long mesescorte;

    private Float valorreconexion;

    private Float valorreconectaemp;

    @Column(name = "valorreconexion_parcial")
    private Float valorreconexionParcial;

    @Column(name = "valorreconectaemp_parcial")
    private Float valorreconectaempParcial;

    @Column(name = "valor_traslado")
    private String valorTraslado;

    @Column(name = "auto_r_ica")
    private Long autoRIca;

    @Column(name = "auto_r_bombe")
    private Long autoRBombe;

    @Column(name = "auto_r_riva")
    private Long autoRRiva;

    @Column(name = "auto_r_fuente")
    private Long autoRFuente;

    @Column(name = "porcent_reconecta")
    private Long porcentReconecta;

    @Column(name = "uvt_minino_reteiva")
    private Float uvtMininoReteiva;

    @Column(name = "uvt_minimo_retefuente")
    private Float uvtMinimoRetefuente;

    @Column(name = "uvt_minimo_reteica")
    private Float uvtMinimoReteica;

    @Column(name = "uvt_minimo_bomberil")
    private Float uvtMinimoBomberil;

    @Column(name = "pucc_interesessiniva")
    private String puccInteresessiniva;

    @Column(name = "niff_interesessiniva")
    private String niffInteresessiniva;

    @Column(name = "pucc_impocomsumosiniva")
    private String puccImpocomsumosiniva;

    @Column(name = "niff_impocomsumosiniva")
    private String niffImpocomsumosiniva;

    @Column(name = "pucc_instalacionsiniva")
    private String puccInstalacionsiniva;

    @Column(name = "niff_instalacionsiniva")
    private String niffInstalacionsiniva;

    @Column(name = "pucc_reconexionsiniva")
    private String puccReconexionsiniva;

    @Column(name = "niff_reconexionsiniva")
    private String niffReconexionsiniva;

    @Column(name = "pucc_trasladosiniva")
    private String puccTrasladosiniva;

    @Column(name = "niff_transladosiniva")
    private String niffTransladosiniva;

    @Column(name = "pucc_materialessiniva")
    private String puccMaterialessiniva;

    @Column(name = "niff_materialessiniva")
    private String niffMaterialessiniva;

    @Column(name = "niff_otrossiniva")
    private String niffOtrossiniva;

    @Column(name = "pucc_otrossiniva")
    private String puccOtrossiniva;

    @Column(name = "pucc_pagosadelantadpsiniva")
    private String puccPagosadelantadpsiniva;

    @Column(name = "niff_pagosadelantadpsiniva")
    private String niffPagosadelantadpsiniva;

    @Column(name = "sms_paracorte")
    private Long smsParacorte;

    @Column(name = "sms_cortado")
    private Long smsCortado;

    @Column(name = "sms_recordatorio")
    private Long smsRecordatorio;

    @Column(name = "sms_paracorte_cuerpo")
    private String smsParacorteCuerpo;

    @Column(name = "sms_cortado_cuerpo")
    private String smsCortadoCuerpo;

    @Column(name = "sms_recordatorio_cuerpo")
    private String smsRecordatorioCuerpo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getImpocomsumo() {
        return impocomsumo;
    }

    public void setImpocomsumo(Long impocomsumo) {
        this.impocomsumo = impocomsumo;
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

    public String getPuccsiniva() {
        return puccsiniva;
    }

    public void setPuccsiniva(String puccsiniva) {
        this.puccsiniva = puccsiniva;
    }

    public String getNiffsiniva() {
        return niffsiniva;
    }

    public void setNiffsiniva(String niffsiniva) {
        this.niffsiniva = niffsiniva;
    }

    public String getPuccIntereses() {
        return puccIntereses;
    }

    public void setPuccIntereses(String puccIntereses) {
        this.puccIntereses = puccIntereses;
    }

    public String getNiffIntereses() {
        return niffIntereses;
    }

    public void setNiffIntereses(String niffIntereses) {
        this.niffIntereses = niffIntereses;
    }

    public String getPuccImpocomsumo() {
        return puccImpocomsumo;
    }

    public void setPuccImpocomsumo(String puccImpocomsumo) {
        this.puccImpocomsumo = puccImpocomsumo;
    }

    public String getNiffimpocomsumo() {
        return niffimpocomsumo;
    }

    public void setNiffimpocomsumo(String niffimpocomsumo) {
        this.niffimpocomsumo = niffimpocomsumo;
    }

    public String getPuccTraslado() {
        return puccTraslado;
    }

    public void setPuccTraslado(String puccTraslado) {
        this.puccTraslado = puccTraslado;
    }

    public String getNiffTranslado() {
        return niffTranslado;
    }

    public void setNiffTranslado(String niffTranslado) {
        this.niffTranslado = niffTranslado;
    }

    public String getPuccMateriales() {
        return puccMateriales;
    }

    public void setPuccMateriales(String puccMateriales) {
        this.puccMateriales = puccMateriales;
    }

    public String getNiffMateriales() {
        return niffMateriales;
    }

    public void setNiffMateriales(String niffMateriales) {
        this.niffMateriales = niffMateriales;
    }

    public String getPuccReconexion() {
        return puccReconexion;
    }

    public void setPuccReconexion(String puccReconexion) {
        this.puccReconexion = puccReconexion;
    }

    public String getNiffReconexion() {
        return niffReconexion;
    }

    public void setNiffReconexion(String niffReconexion) {
        this.niffReconexion = niffReconexion;
    }

    public String getPuccPagosadelantadp() {
        return puccPagosadelantadp;
    }

    public void setPuccPagosadelantadp(String puccPagosadelantadp) {
        this.puccPagosadelantadp = puccPagosadelantadp;
    }

    public String getNiffPagosadelantadp() {
        return niffPagosadelantadp;
    }

    public void setNiffPagosadelantadp(String niffPagosadelantadp) {
        this.niffPagosadelantadp = niffPagosadelantadp;
    }

    public String getPuccInstalacion() {
        return puccInstalacion;
    }

    public void setPuccInstalacion(String puccInstalacion) {
        this.puccInstalacion = puccInstalacion;
    }

    public String getNiffInstalacion() {
        return niffInstalacion;
    }

    public void setNiffInstalacion(String niffInstalacion) {
        this.niffInstalacion = niffInstalacion;
    }

    public String getPuccNotasceedito() {
        return puccNotasceedito;
    }

    public void setPuccNotasceedito(String puccNotasceedito) {
        this.puccNotasceedito = puccNotasceedito;
    }

    public String getNiffNotasceedito() {
        return niffNotasceedito;
    }

    public void setNiffNotasceedito(String niffNotasceedito) {
        this.niffNotasceedito = niffNotasceedito;
    }

    public String getPuccNotasceeditoExcentas() {
        return puccNotasceeditoExcentas;
    }

    public void setPuccNotasceeditoExcentas(String puccNotasceeditoExcentas) {
        this.puccNotasceeditoExcentas = puccNotasceeditoExcentas;
    }

    public String getNiffNotasceeditoExcentas() {
        return niffNotasceeditoExcentas;
    }

    public void setNiffNotasceeditoExcentas(String niffNotasceeditoExcentas) {
        this.niffNotasceeditoExcentas = niffNotasceeditoExcentas;
    }

    public String getPuccOtros() {
        return puccOtros;
    }

    public void setPuccOtros(String puccOtros) {
        this.puccOtros = puccOtros;
    }

    public String getNiffOtros() {
        return niffOtros;
    }

    public void setNiffOtros(String niffOtros) {
        this.niffOtros = niffOtros;
    }

    public Long getIntereses() {
        return intereses;
    }

    public void setIntereses(Long intereses) {
        this.intereses = intereses;
    }

    public String getIva_str_0() {
        return iva_str_0;
    }

    public void setIva_str_0(String iva_str_0) {
        this.iva_str_0 = iva_str_0;
    }

    public String getIvaStr1() {
        return ivaStr1;
    }

    public void setIvaStr1(String ivaStr1) {
        this.ivaStr1 = ivaStr1;
    }

    public String getIvaStr2() {
        return ivaStr2;
    }

    public void setIvaStr2(String ivaStr2) {
        this.ivaStr2 = ivaStr2;
    }

    public String getIvaStr3() {
        return ivaStr3;
    }

    public void setIvaStr3(String ivaStr3) {
        this.ivaStr3 = ivaStr3;
    }

    public String getIvaStr4() {
        return ivaStr4;
    }

    public void setIvaStr4(String ivaStr4) {
        this.ivaStr4 = ivaStr4;
    }

    public String getIvaStr5() {
        return ivaStr5;
    }

    public void setIvaStr5(String ivaStr5) {
        this.ivaStr5 = ivaStr5;
    }

    public String getIvaStr6() {
        return ivaStr6;
    }

    public void setIvaStr6(String ivaStr6) {
        this.ivaStr6 = ivaStr6;
    }

    public String getIvaStr7() {
        return ivaStr7;
    }

    public void setIvaStr7(String ivaStr7) {
        this.ivaStr7 = ivaStr7;
    }

    public String getIvaStr8() {
        return ivaStr8;
    }

    public void setIvaStr8(String ivaStr8) {
        this.ivaStr8 = ivaStr8;
    }

    public String getFormatoOrden() {
        return formatoOrden;
    }

    public void setFormatoOrden(String formatoOrden) {
        this.formatoOrden = formatoOrden;
    }

    public String getValoruvt() {
        return valoruvt;
    }

    public void setValoruvt(String valoruvt) {
        this.valoruvt = valoruvt;
    }

    public String getReteiva() {
        return reteiva;
    }

    public void setReteiva(String reteiva) {
        this.reteiva = reteiva;
    }

    public String getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(String retefuente) {
        this.retefuente = retefuente;
    }

    public String getReteica() {
        return reteica;
    }

    public void setReteica(String reteica) {
        this.reteica = reteica;
    }

    public String getBomberil() {
        return bomberil;
    }

    public void setBomberil(String bomberil) {
        this.bomberil = bomberil;
    }

    public Long getLimitepago() {
        return limitepago;
    }

    public void setLimitepago(Long limitepago) {
        this.limitepago = limitepago;
    }

    public Long getMesescorte() {
        return mesescorte;
    }

    public void setMesescorte(Long mesescorte) {
        this.mesescorte = mesescorte;
    }

    public Float getValorreconexion() {
        return valorreconexion;
    }

    public void setValorreconexion(Float valorreconexion) {
        this.valorreconexion = valorreconexion;
    }

    public Float getValorreconectaemp() {
        return valorreconectaemp;
    }

    public void setValorreconectaemp(Float valorreconectaemp) {
        this.valorreconectaemp = valorreconectaemp;
    }

    public Float getValorreconexionParcial() {
        return valorreconexionParcial;
    }

    public void setValorreconexionParcial(Float valorreconexionParcial) {
        this.valorreconexionParcial = valorreconexionParcial;
    }

    public Float getValorreconectaempParcial() {
        return valorreconectaempParcial;
    }

    public void setValorreconectaempParcial(Float valorreconectaempParcial) {
        this.valorreconectaempParcial = valorreconectaempParcial;
    }

    public String getValorTraslado() {
        return valorTraslado;
    }

    public void setValorTraslado(String valorTraslado) {
        this.valorTraslado = valorTraslado;
    }

    public Long getAutoRIca() {
        return autoRIca;
    }

    public void setAutoRIca(Long autoRIca) {
        this.autoRIca = autoRIca;
    }

    public Long getAutoRBombe() {
        return autoRBombe;
    }

    public void setAutoRBombe(Long autoRBombe) {
        this.autoRBombe = autoRBombe;
    }

    public Long getAutoRRiva() {
        return autoRRiva;
    }

    public void setAutoRRiva(Long autoRRiva) {
        this.autoRRiva = autoRRiva;
    }

    public Long getAutoRFuente() {
        return autoRFuente;
    }

    public void setAutoRFuente(Long autoRFuente) {
        this.autoRFuente = autoRFuente;
    }

    public Long getPorcentReconecta() {
        return porcentReconecta;
    }

    public void setPorcentReconecta(Long porcentReconecta) {
        this.porcentReconecta = porcentReconecta;
    }

    public Float getUvtMininoReteiva() {
        return uvtMininoReteiva;
    }

    public void setUvtMininoReteiva(Float uvtMininoReteiva) {
        this.uvtMininoReteiva = uvtMininoReteiva;
    }

    public Float getUvtMinimoRetefuente() {
        return uvtMinimoRetefuente;
    }

    public void setUvtMinimoRetefuente(Float uvtMinimoRetefuente) {
        this.uvtMinimoRetefuente = uvtMinimoRetefuente;
    }

    public Float getUvtMinimoReteica() {
        return uvtMinimoReteica;
    }

    public void setUvtMinimoReteica(Float uvtMinimoReteica) {
        this.uvtMinimoReteica = uvtMinimoReteica;
    }

    public Float getUvtMinimoBomberil() {
        return uvtMinimoBomberil;
    }

    public void setUvtMinimoBomberil(Float uvtMinimoBomberil) {
        this.uvtMinimoBomberil = uvtMinimoBomberil;
    }

    public String getPuccInteresessiniva() {
        return puccInteresessiniva;
    }

    public void setPuccInteresessiniva(String puccInteresessiniva) {
        this.puccInteresessiniva = puccInteresessiniva;
    }

    public String getNiffInteresessiniva() {
        return niffInteresessiniva;
    }

    public void setNiffInteresessiniva(String niffInteresessiniva) {
        this.niffInteresessiniva = niffInteresessiniva;
    }

    public String getPuccImpocomsumosiniva() {
        return puccImpocomsumosiniva;
    }

    public void setPuccImpocomsumosiniva(String puccImpocomsumosiniva) {
        this.puccImpocomsumosiniva = puccImpocomsumosiniva;
    }

    public String getNiffImpocomsumosiniva() {
        return niffImpocomsumosiniva;
    }

    public void setNiffImpocomsumosiniva(String niffImpocomsumosiniva) {
        this.niffImpocomsumosiniva = niffImpocomsumosiniva;
    }

    public String getPuccInstalacionsiniva() {
        return puccInstalacionsiniva;
    }

    public void setPuccInstalacionsiniva(String puccInstalacionsiniva) {
        this.puccInstalacionsiniva = puccInstalacionsiniva;
    }

    public String getNiffInstalacionsiniva() {
        return niffInstalacionsiniva;
    }

    public void setNiffInstalacionsiniva(String niffInstalacionsiniva) {
        this.niffInstalacionsiniva = niffInstalacionsiniva;
    }

    public String getPuccReconexionsiniva() {
        return puccReconexionsiniva;
    }

    public void setPuccReconexionsiniva(String puccReconexionsiniva) {
        this.puccReconexionsiniva = puccReconexionsiniva;
    }

    public String getNiffReconexionsiniva() {
        return niffReconexionsiniva;
    }

    public void setNiffReconexionsiniva(String niffReconexionsiniva) {
        this.niffReconexionsiniva = niffReconexionsiniva;
    }

    public String getPuccTrasladosiniva() {
        return puccTrasladosiniva;
    }

    public void setPuccTrasladosiniva(String puccTrasladosiniva) {
        this.puccTrasladosiniva = puccTrasladosiniva;
    }

    public String getNiffTransladosiniva() {
        return niffTransladosiniva;
    }

    public void setNiffTransladosiniva(String niffTransladosiniva) {
        this.niffTransladosiniva = niffTransladosiniva;
    }

    public String getPuccMaterialessiniva() {
        return puccMaterialessiniva;
    }

    public void setPuccMaterialessiniva(String puccMaterialessiniva) {
        this.puccMaterialessiniva = puccMaterialessiniva;
    }

    public String getNiffMaterialessiniva() {
        return niffMaterialessiniva;
    }

    public void setNiffMaterialessiniva(String niffMaterialessiniva) {
        this.niffMaterialessiniva = niffMaterialessiniva;
    }

    public String getNiffOtrossiniva() {
        return niffOtrossiniva;
    }

    public void setNiffOtrossiniva(String niffOtrossiniva) {
        this.niffOtrossiniva = niffOtrossiniva;
    }

    public String getPuccOtrossiniva() {
        return puccOtrossiniva;
    }

    public void setPuccOtrossiniva(String puccOtrossiniva) {
        this.puccOtrossiniva = puccOtrossiniva;
    }

    public String getPuccPagosadelantadpsiniva() {
        return puccPagosadelantadpsiniva;
    }

    public void setPuccPagosadelantadpsiniva(String puccPagosadelantadpsiniva) {
        this.puccPagosadelantadpsiniva = puccPagosadelantadpsiniva;
    }

    public String getNiffPagosadelantadpsiniva() {
        return niffPagosadelantadpsiniva;
    }

    public void setNiffPagosadelantadpsiniva(String niffPagosadelantadpsiniva) {
        this.niffPagosadelantadpsiniva = niffPagosadelantadpsiniva;
    }

    public Long getSmsParacorte() {
        return smsParacorte;
    }

    public void setSmsParacorte(Long smsParacorte) {
        this.smsParacorte = smsParacorte;
    }

    public Long getSmsCortado() {
        return smsCortado;
    }

    public void setSmsCortado(Long smsCortado) {
        this.smsCortado = smsCortado;
    }

    public Long getSmsRecordatorio() {
        return smsRecordatorio;
    }

    public void setSmsRecordatorio(Long smsRecordatorio) {
        this.smsRecordatorio = smsRecordatorio;
    }

    public String getSmsParacorteCuerpo() {
        return smsParacorteCuerpo;
    }

    public void setSmsParacorteCuerpo(String smsParacorteCuerpo) {
        this.smsParacorteCuerpo = smsParacorteCuerpo;
    }

    public String getSmsCortadoCuerpo() {
        return smsCortadoCuerpo;
    }

    public void setSmsCortadoCuerpo(String smsCortadoCuerpo) {
        this.smsCortadoCuerpo = smsCortadoCuerpo;
    }

    public String getSmsRecordatorioCuerpo() {
        return smsRecordatorioCuerpo;
    }

    public void setSmsRecordatorioCuerpo(String smsRecordatorioCuerpo) {
        this.smsRecordatorioCuerpo = smsRecordatorioCuerpo;
    }
}
