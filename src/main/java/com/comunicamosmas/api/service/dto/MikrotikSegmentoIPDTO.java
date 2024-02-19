package com.comunicamosmas.api.service.dto;

public class MikrotikSegmentoIPDTO{

    private int id_segmento_ip;

    private int id_estacion;

    private String segmento;

    private int id_pool;

    private String estado;

    public int getId_segmento_ip() {
        return id_segmento_ip;
    }

    public void setId_segmento_ip(int id_segmento_ip) {
        this.id_segmento_ip = id_segmento_ip;
    }

    public int getId_estacion() {
        return id_estacion;
    }

    public void setId_estacion(int id_estacion) {
        this.id_estacion = id_estacion;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public int getId_pool() {
        return id_pool;
    }

    public void setId_pool(int id_pool) {
        this.id_pool = id_pool;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}