package com.comunicamosmas.api.service.dto;

public class ContratosFirmasDTO { 
     
public class Datos{
    private String id_contrato;
    private String id_cliente; 
    private Integer idServicio;
    private String nombre_asesor;
    private String registro;
    private String vigencia;
    private String activacion;
    private String comentario;
    private String reconexion;
    private DatosSuscriptor datos_suscriptor;
    private DatosContacto datos_contacto;
    private DatosServicio datos_servicio;
    private ServiciosAdicionales servicios_adicionales;
    private ClausulaPermanencia clausula_permanencia;

    public String getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(String id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_asesor() {
        return nombre_asesor;
    }

    public void setNombre_asesor(String nombre_asesor) {
        this.nombre_asesor = nombre_asesor;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getActivacion() {
        return activacion;
    }

    public void setActivacion(String activacion) {
        this.activacion = activacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getReconexion() {
        return reconexion;
    }

    public void setReconexion(String reconexion) {
        this.reconexion = reconexion;
    }

    public DatosSuscriptor getDatos_suscriptor() {
        return datos_suscriptor;
    }

    public void setDatos_suscriptor(DatosSuscriptor datos_suscriptor) {
        this.datos_suscriptor = datos_suscriptor;
    }

    public DatosContacto getDatos_contacto() {
        return datos_contacto;
    }

    public void setDatos_contacto(DatosContacto datos_contacto) {
        this.datos_contacto = datos_contacto;
    }

    public DatosServicio getDatos_servicio() {
        return datos_servicio;
    }

    public void setDatos_servicio(DatosServicio datos_servicio) {
        this.datos_servicio = datos_servicio;
    }

    public ServiciosAdicionales getServicios_adicionales() {
        return servicios_adicionales;
    }

    public void setServicios_adicionales(ServiciosAdicionales servicios_adicionales) {
        this.servicios_adicionales = servicios_adicionales;
    }

    public ClausulaPermanencia getClausula_permanencia() {
        return clausula_permanencia;
    }

    public void setClausula_permanencia(ClausulaPermanencia clausula_permanencia) {
        this.clausula_permanencia = clausula_permanencia;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }
}

 

public   class DatosSuscriptor {
    public String fisico;
    public String nombre;
    public String identificacion;
    public String correo;
    public String celular;
    public String estrato;

    public String getFisico() {
        return fisico;
    }

    public void setFisico(String fisico) {
        this.fisico = fisico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

}

public    class DatosServicio {
    public String mensualidad;
    public String instalacion;
    public String velocidad;
    public String concepto;

    public String getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(String mensualidad) {
        this.mensualidad = mensualidad;
    }

    public String getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(String instalacion) {
        this.instalacion = instalacion;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

}

public   class DatosContacto {
    public String direccion;
    public String barrio;
    public String municipio;
    public String departamento;
    public String direccion_facturacion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion_facturacion() {
        return direccion_facturacion;
    }

    public void setDireccion_facturacion(String direccion_facturacion) {
        this.direccion_facturacion = direccion_facturacion;
    }

}

public   class ClausulaPermanencia {
    public String inicio;
    public String fin;
    public String mes_1;
    public String mes_2;
    public String mes_3;
    public String mes_4;
    public String mes_5;
    public String mes_6;
    public String mes_7;
    public String mes_8;
    public String mes_9;
    public String mes_10;
    public String mes_11;
    public String mes_12;

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getMes_1() {
        return mes_1;
    }

    public void setMes_1(String mes_1) {
        this.mes_1 = mes_1;
    }

    public String getMes_2() {
        return mes_2;
    }

    public void setMes_2(String mes_2) {
        this.mes_2 = mes_2;
    }

    public String getMes_3() {
        return mes_3;
    }

    public void setMes_3(String mes_3) {
        this.mes_3 = mes_3;
    }

    public String getMes_4() {
        return mes_4;
    }

    public void setMes_4(String mes_4) {
        this.mes_4 = mes_4;
    }

    public String getMes_5() {
        return mes_5;
    }

    public void setMes_5(String mes_5) {
        this.mes_5 = mes_5;
    }

    public String getMes_6() {
        return mes_6;
    }

    public void setMes_6(String mes_6) {
        this.mes_6 = mes_6;
    }

    public String getMes_7() {
        return mes_7;
    }

    public void setMes_7(String mes_7) {
        this.mes_7 = mes_7;
    }

    public String getMes_8() {
        return mes_8;
    }

    public void setMes_8(String mes_8) {
        this.mes_8 = mes_8;
    }

    public String getMes_9() {
        return mes_9;
    }

    public void setMes_9(String mes_9) {
        this.mes_9 = mes_9;
    }

    public String getMes_10() {
        return mes_10;
    }

    public void setMes_10(String mes_10) {
        this.mes_10 = mes_10;
    }

    public String getMes_11() {
        return mes_11;
    }

    public void setMes_11(String mes_11) {
        this.mes_11 = mes_11;
    }

    public String getMes_12() {
        return mes_12;
    }

    public void setMes_12(String mes_12) {
        this.mes_12 = mes_12;
    }

}

public   class ServiciosAdicionales {
    public String mensualidad;
    public String concepto;

    public String getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(String mensualidad) {
        this.mensualidad = mensualidad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    }

 
 }