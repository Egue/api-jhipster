package com.comunicamosmas.api.service.dto;

public class UpdateRemoteAddress {
    private Long idWinmaxPass;

    private Long idEstacion;

    private SegmentoDTO segmento;

    public Long getIdWinmaxPass() {
        return idWinmaxPass;
    }

    public void setIdWinmaxPass(Long idWinmaxPass) {
        this.idWinmaxPass = idWinmaxPass;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public SegmentoDTO getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoDTO segmento) {
        this.segmento = segmento;
    }

    
}
