package com.comunicamosmas.api.service.dto;
 

public class MikrotikHijoSimpleDTO {
    private String id;
 
    private String idPadre;
 
    private String idIp;

    private String target;
 
    private String limitAt;
 
    private String maxLimit;

    private String name;
 
    private String idContrato;

    private String queue;

    private String estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getIdIp() {
        return idIp;
    }

    public void setIdIp(String idIp) {
        this.idIp = idIp;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLimitAt() {
        return limitAt;
    }

    public void setLimitAt(String limitAt) {
        this.limitAt = limitAt;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
