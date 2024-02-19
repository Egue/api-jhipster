package com.comunicamosmas.api.service.dto;

public class SegmentoIPDTO {
    /*
     * {
     * "idEstacion": "2",
     * "segmento": {
     * "ranges": "192.168.68.2-192.168.68.126",
     * "name": "dhcp_pool0",
     * ".id": "*1",
     * "concat": "dhcp_pool0|192.168.68.2-192.168.68.126"
     * },
     * "idPool": 33,
     * "estado": "vacio"
     * }
     */
    private Long id;
    
    private Long idEstacion;

    private Pool segmento;

    private Long idPool;

    private String estado;

    @Override
    public String toString() {
        return "SegmentoIPDTO [idEstacion=" + idEstacion + ", segmento=" + segmento + ", idPool=" + idPool + ", estado="
                + estado + "]";
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Pool getSegmento() {
        return segmento;
    }

    public void setSegmento(Pool segmento) {
        this.segmento = segmento;
    }

    public Long getIdPool() {
        return idPool;
    }

    public void setIdPool(Long idPool) {
        this.idPool = idPool;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static class Pool {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
