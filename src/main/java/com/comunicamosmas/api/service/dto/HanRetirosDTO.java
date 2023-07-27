package com.comunicamosmas.api.service.dto;
 
import java.util.List;
 

public class HanRetirosDTO {

    private Integer id;

    private String tipo;

    private String descripcion;
 
    private Integer id_user;
     
    private String created_at;
 
    private Integer id_contrato;
 

    private String estado;
 
    private String cierre_fecha;
 
    private String url_documento;
  
    private String nameUser;

    private String cliente;

    
    
    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(Integer id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCierre_fecha() {
        return cierre_fecha;
    }

    public void setCierre_fecha(String cierre_fecha) {
        this.cierre_fecha = cierre_fecha;
    }

    public String getUrl_documento() {
        return url_documento;
    }

    public void setUrl_documento(String url_documento) {
        this.url_documento = url_documento;
    }

     

    
    


}
