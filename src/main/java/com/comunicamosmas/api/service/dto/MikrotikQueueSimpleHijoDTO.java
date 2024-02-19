package com.comunicamosmas.api.service.dto;

public class MikrotikQueueSimpleHijoDTO {
    private Long idPlan;

    private Long idEstacion;

    private Segmento segmento;

    private Long idContrato;

    private Long idPadre;

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

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public class Segmento{
    
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

        
    }

    
}
