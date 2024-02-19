package com.comunicamosmas.api.service.dto;

public class MikrotikQueueSimplePadreDTO {
    
    private Long idPlan;

    private Long idEstacion;


    private Segmento segmento;
    
    
    public Long getIdPlan() {
        return idPlan;
    }


    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }


    public Long getIdEstacion() {
        return idEstacion;
    }


    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }


    public Segmento getSegmento() {
        return segmento;
    }


    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }


    public class Segmento {
        private long estado;

    private long id;

    private long idSegmentoIp;

    private String ip;

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSegmentoIp() {
        return idSegmentoIp;
    }

    public void setIdSegmentoIp(long idSegmentoIp) {
        this.idSegmentoIp = idSegmentoIp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "SegmentoIpDTO2 [estado=" + estado + ", id=" + id + ", idSegmentoIp=" + idSegmentoIp + ", ip=" + ip
                + "]";
    }
    }    
}
