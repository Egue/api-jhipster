package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ordenes")
@Entity
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @Column(name = "tipo_orden")
    private Long tipoOrden;

    private String refiere;

    @Column(name = "causa_solicitud")
    private String causaSolicitud;

    @Column(name = "numero_a")
    private Long numeroA;

    @Column(name = "numero_b")
    private Long numeroB;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_direccion")
    private Long idDireccion;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_estacion")
    private Long idEstacion;

    @Column(name = "id_zona")
    private Long idZona;

    @Column(name = "fechaf_registra")
    private Long fechafRegistra;

    @Column(name = "fechaf_solicita")
    private Long fechafSolicita;

    @Column(name = "fechaf_asigna")
    private Long fechafAsigna;

    @Column(name = "fechaf_asiste")
    private Long fechafAsiste;

    @Column(name = "fechaf_anula")
    private Long fechafAnula;

    @Column(name = "fechaf_descarga")
    private Long fechafDescarga;

    @Column(name = "hora_asiste_inicio")
    private String horaAsisteInicio;

    @Column(name = "hota_asiste_fin")
    private String hotaAsisteFin;

    private Long estado;

    private String nota;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_usuario_registra")
    private Long idUsuarioRegistra;

    @Column(name = "id_usuario_asigna	")
    private Long idUsuarioAsigna;

    @Column(name = "id_usuario_ejecuta")
    private Long idUsuarioEjecuta;

    @Column(name = "id_usuario_descarga")
    private Long idUsuarioDescarga;

    @Column(name = "id_usuario_anula")
    private Long idUsuarioAnula;

    @Column(name = "anula_justifica")
    private String anulaJustifica;

    private String a;

    private String b;

    private String c;

    private String d;

    private String e;

    private String f;

    private String g;

    private String h;

    private String i;

    private String j;

    @Column(name = "ultima_dow")
    private String ultimaDow;

    private Long abierta;

    private Long anulada;

    @Column(name = "nota_final")
    private Long notaFinal;

    private Long winmax;

    @Column(name = "winmax_id_usuario")
    private Long winmaxIdUsuario;

    @Column(name = "winmax_marca")
    private String winmaxMarca;

    @Column(name = "id_ticket_soporte")
    private Long idTicketSoporte;

    @Column(name = "pdf_descarga_fecha")
    private String pdfDescargaFecha;

    @Column(name = "pdf_descarga_usuario")
    private Long pdfDescargaUsuario;

    @Column(name = "id_tecnologia")
    private Long idTecnologia;

    @Column(name = "tipo_reconecta")
    private Long tipoReconecta;

    @Column(name = "api_automatica")
    private Long apiAutomatica;

    @Column(name = "log_api")
    private String logApi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(Long tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getRefiere() {
        return refiere;
    }

    public void setRefiere(String refiere) {
        this.refiere = refiere;
    }

    public String getCausaSolicitud() {
        return causaSolicitud;
    }

    public void setCausaSolicitud(String causaSolicitud) {
        this.causaSolicitud = causaSolicitud;
    }

    public Long getNumeroA() {
        return numeroA;
    }

    public void setNumeroA(Long numeroA) {
        this.numeroA = numeroA;
    }

    public Long getNumeroB() {
        return numeroB;
    }

    public void setNumeroB(Long numeroB) {
        this.numeroB = numeroB;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public Long getFechafRegistra() {
        return fechafRegistra;
    }

    public void setFechafRegistra(Long fechafRegistra) {
        this.fechafRegistra = fechafRegistra;
    }

    public Long getFechafSolicita() {
        return fechafSolicita;
    }

    public void setFechafSolicita(Long fechafSolicita) {
        this.fechafSolicita = fechafSolicita;
    }

    public Long getFechafAsigna() {
        return fechafAsigna;
    }

    public void setFechafAsigna(Long fechafAsigna) {
        this.fechafAsigna = fechafAsigna;
    }

    public Long getFechafAsiste() {
        return fechafAsiste;
    }

    public void setFechafAsiste(Long fechafAsiste) {
        this.fechafAsiste = fechafAsiste;
    }

    public Long getFechafAnula() {
        return fechafAnula;
    }

    public void setFechafAnula(Long fechafAnula) {
        this.fechafAnula = fechafAnula;
    }

    public Long getFechafDescarga() {
        return fechafDescarga;
    }

    public void setFechafDescarga(Long fechafDescarga) {
        this.fechafDescarga = fechafDescarga;
    }

    public String getHoraAsisteInicio() {
        return horaAsisteInicio;
    }

    public void setHoraAsisteInicio(String horaAsisteInicio) {
        this.horaAsisteInicio = horaAsisteInicio;
    }

    public String getHotaAsisteFin() {
        return hotaAsisteFin;
    }

    public void setHotaAsisteFin(String hotaAsisteFin) {
        this.hotaAsisteFin = hotaAsisteFin;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(Long idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    public Long getIdUsuarioAsigna() {
        return idUsuarioAsigna;
    }

    public void setIdUsuarioAsigna(Long idUsuarioAsigna) {
        this.idUsuarioAsigna = idUsuarioAsigna;
    }

    public Long getIdUsuarioEjecuta() {
        return idUsuarioEjecuta;
    }

    public void setIdUsuarioEjecuta(Long idUsuarioEjecuta) {
        this.idUsuarioEjecuta = idUsuarioEjecuta;
    }

    public Long getIdUsuarioDescarga() {
        return idUsuarioDescarga;
    }

    public void setIdUsuarioDescarga(Long idUsuarioDescarga) {
        this.idUsuarioDescarga = idUsuarioDescarga;
    }

    public Long getIdUsuarioAnula() {
        return idUsuarioAnula;
    }

    public void setIdUsuarioAnula(Long idUsuarioAnula) {
        this.idUsuarioAnula = idUsuarioAnula;
    }

    public String getAnulaJustifica() {
        return anulaJustifica;
    }

    public void setAnulaJustifica(String anulaJustifica) {
        this.anulaJustifica = anulaJustifica;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        f = f;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    public String getUltimaDow() {
        return ultimaDow;
    }

    public void setUltimaDow(String ultimaDow) {
        this.ultimaDow = ultimaDow;
    }

    public Long getAbierta() {
        return abierta;
    }

    public void setAbierta(Long abierta) {
        this.abierta = abierta;
    }

    public Long getAnulada() {
        return anulada;
    }

    public void setAnulada(Long anulada) {
        this.anulada = anulada;
    }

    public Long getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Long notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Long getWinmax() {
        return winmax;
    }

    public void setWinmax(Long winmax) {
        this.winmax = winmax;
    }

    public Long getWinmaxIdUsuario() {
        return winmaxIdUsuario;
    }

    public void setWinmaxIdUsuario(Long winmaxIdUsuario) {
        this.winmaxIdUsuario = winmaxIdUsuario;
    }

    public String getWinmaxMarca() {
        return winmaxMarca;
    }

    public void setWinmaxMarca(String winmaxMarca) {
        this.winmaxMarca = winmaxMarca;
    }

    public Long getIdTicketSoporte() {
        return idTicketSoporte;
    }

    public void setIdTicketSoporte(Long idTicketSoporte) {
        this.idTicketSoporte = idTicketSoporte;
    }

    public String getPdfDescargaFecha() {
        return pdfDescargaFecha;
    }

    public void setPdfDescargaFecha(String pdfDescargaFecha) {
        this.pdfDescargaFecha = pdfDescargaFecha;
    }

    public Long getPdfDescargaUsuario() {
        return pdfDescargaUsuario;
    }

    public void setPdfDescargaUsuario(Long pdfDescargaUsuario) {
        this.pdfDescargaUsuario = pdfDescargaUsuario;
    }

    public Long getIdTecnologia() {
        return idTecnologia;
    }

    public void setIdTecnologia(Long idTecnologia) {
        this.idTecnologia = idTecnologia;
    }

    public Long getTipoReconecta() {
        return tipoReconecta;
    }

    public void setTipoReconecta(Long tipoReconecta) {
        this.tipoReconecta = tipoReconecta;
    }

    public Long getApiAutomatica() {
        return apiAutomatica;
    }

    public void setApiAutomatica(Long apiAutomatica) {
        this.apiAutomatica = apiAutomatica;
    }

    public String getLogApi() {
        return logApi;
    }

    public void setLogApi(String logApi) {
        this.logApi = logApi;
    }
}
