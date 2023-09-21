package com.comunicamosmas.api.domain;

import java.io.Serializable; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "clientes")
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "nombre_primer")
    private String nombrePrimer;

    @Column(name = "nombre_segundo")
    private String nombreSegundo;

    private String genero;

    @Column(name = "nombres_rep")
    private String nombresRep;

    @Column(name = "apellidos_rep")
    private String apellidosRep;

    @Column(name = "id_documento")
    private String idDocumento;

    private Long documento;

    private Long dv;

    @Column(name = "f_nacimiento")
    private Integer fNacimiento;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "tipo_vivienda")
    private String tipoVivienda;

    private Long telefono;

    @Column(name = "celular_a")
    private String celularA;

    @Column(name = "id_opera_a")
    private Long idOperaA;

    @Column(name = "celular_b")
    private String celularB;

    @Column(name = "id_opera_b")
    private Long idOperaB;

    private String mail;

    private Integer estrato;

    private String observaciones;

   // private Instant marca;

    private String sha;

    private Long bomberil;

    @Column(name = "autoriza_sms")
    private Long autorizaSms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombrePrimer() {
        return nombrePrimer;
    }

    public void setNombrePrimer(String nombrePrimer) {
        this.nombrePrimer = nombrePrimer;
    }

    public String getNombreSegundo() {
        return nombreSegundo;
    }

    public void setNombreSegundo(String nombreSegundo) {
        this.nombreSegundo = nombreSegundo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombresRep() {
        return nombresRep;
    }

    public void setNombresRep(String nombresRep) {
        this.nombresRep = nombresRep;
    }

    public String getApellidosRep() {
        return apellidosRep;
    }

    public void setApellidosRep(String apellidosRep) {
        this.apellidosRep = apellidosRep;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public Long getDv() {
        return dv;
    }

    public void setDv(Long dv) {
        this.dv = dv;
    }

    public Integer getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Integer fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCelularA() {
        return celularA;
    }

    public void setCelularA(String celularA) {
        this.celularA = celularA;
    }

    public Long getIdOperaA() {
        return idOperaA;
    }

    public void setIdOperaA(Long idOperaA) {
        this.idOperaA = idOperaA;
    }

    public String getCelularB() {
        return celularB;
    }

    public void setCelularB(String celularB) {
        this.celularB = celularB;
    }

    public Long getIdOperaB() {
        return idOperaB;
    }

    public void setIdOperaB(Long idOperaB) {
        this.idOperaB = idOperaB;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getEstrato() {
        return estrato;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /*public Instant getMarca() {
        return marca;
    }*/

    /*public void setMarca(Instant marca) {
        this.marca = marca;
    }*/

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Long getBomberil() {
        return bomberil;
    }

    public void setBomberil(Long bomberil) {
        this.bomberil = bomberil;
    }

    public Long getAutorizaSms() {
        return autorizaSms;
    }

    public void setAutorizaSms(Long autorizaSms) {
        this.autorizaSms = autorizaSms;
    }
}
