package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mikrotik_ip")
@Entity
public class MikrotikIp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ip")
    private Long id;

    @Column(name = "id_segmento_ip")
    private Long idSegmentoIp;

    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
        this.id  =id;
    }

    public Long getIdSegmentoIp() {
        return idSegmentoIp;
    }

    public void setIdSegmentoIp(Long idSegmentoIp) {
        this.idSegmentoIp = idSegmentoIp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    private Long estado;
}
