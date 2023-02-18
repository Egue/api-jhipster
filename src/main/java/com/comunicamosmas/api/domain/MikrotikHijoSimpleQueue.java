package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mikrotik_hijo_simple_queue")
@Entity
public class MikrotikHijoSimpleQueue implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hijo")
    private Long id;

    @Column(name = "id_padre")
    private Long idPadre;

    @Column(name = "id_ip")
    private Long idIp;

    private String target;

    @Column(name = "limit_at")
    private String limitAt;

    @Column(name = "max_limit")
    private String maxLimit;

    private String name;

    @Column(name = "id_contrato")
    private Long idContrato;

    private String queue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public Long getIdIp() {
        return idIp;
    }

    public void setIdIp(Long idIp) {
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

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
