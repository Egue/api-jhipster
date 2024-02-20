package com.comunicamosmas.api.service.dto;

public class SegmentoDTO {

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
