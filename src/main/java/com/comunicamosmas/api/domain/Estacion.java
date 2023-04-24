package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "estaciones")
@Entity
public class Estacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estacion")
    private Long id;

    private String codigo;

    private String nombre;

    private Long tipo;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_bodega")
    private Long idBodega;

    @Column(name = "api_ip")
    private String apiIp;

    @Column(name = "api_port")
    private Long apiPort;

    @Column(name = "api_user")
    private String apiUser;

    @Column(name = "api_pass")
    private String apiPass;

    @Column(name = "web_port")
    private Long webPort;

    @Column(name = "id_zona")
    private Long idZona;

    private String pais;

    private String departamento;

    private String municipio;

    private String barrio;

    @Column(name = "tipo_direccion")
    private String tipoDireccion;

    @Column(name = "a_tipo")
    private String aTipo;

    @Column(name = "a_numero")
    private Long aNumero;

    @Column(name = "a_letra")
    private String aLetra;

    @Column(name = "b_tipo")
    private String bTipo;

    @Column(name = "b_numero")
    private Long bNumero;

    @Column(name = "b_letra")
    private String bLetra;

    private Long numero;

    private String latitud;

    private String longitud;

    private String nota;

    private Long estado;

    //private String marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
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

    public Long getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Long idBodega) {
        this.idBodega = idBodega;
    }

    public String getApiIp() {
        return apiIp;
    }

    public void setApiIp(String apiIp) {
        this.apiIp = apiIp;
    }

    public Long getApiPort() {
        return apiPort;
    }

    public void setApiPort(Long apiPort) {
        this.apiPort = apiPort;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiPass() {
        return apiPass;
    }

    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

    public Long getWebPort() {
        return webPort;
    }

    public void setWebPort(Long webPort) {
        this.webPort = webPort;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getaTipo() {
        return aTipo;
    }

    public void setaTipo(String aTipo) {
        this.aTipo = aTipo;
    }

    public Long getaNumero() {
        return aNumero;
    }

    public void setaNumero(Long aNumero) {
        this.aNumero = aNumero;
    }

    public String getaLetra() {
        return aLetra;
    }

    public void setaLetra(String aLetra) {
        this.aLetra = aLetra;
    }

    public String getbTipo() {
        return bTipo;
    }

    public void setbTipo(String bTipo) {
        this.bTipo = bTipo;
    }

    public Long getbNumero() {
        return bNumero;
    }

    public void setbNumero(Long bNumero) {
        this.bNumero = bNumero;
    }

    public String getbLetra() {
        return bLetra;
    }

    public void setbLetra(String bLetra) {
        this.bLetra = bLetra;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

   /* public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }*/
}
