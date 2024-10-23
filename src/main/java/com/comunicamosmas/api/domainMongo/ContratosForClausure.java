package com.comunicamosmas.api.domainMongo;

import java.io.Serializable;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "contratos_para_clausura")
public class ContratosForClausure implements Serializable{

    @Id
    private ObjectId id;

    private String tipo_cliente;

    private String document;

    private Integer id_contrato;

    private String registrado;

    private String name_cliente;

    private String date_reporte;

    

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(Integer id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }

    public String getName_cliente() {
        return name_cliente;
    }

    public void setName_cliente(String name_cliente) {
        this.name_cliente = name_cliente;
    }

    public String getDate_reporte() {
        return date_reporte;
    }

    public void setDate_reporte(String date_reporte) {
        this.date_reporte = date_reporte;
    }

    
 
}
