package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mikrotik_padre_simple_queue")
@Entity
public class MikrotikPadreSimpleQueue implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_padre")
    private Long id;

    @Column(name = "id_tarifa_reuso")
    private Long idTarifaReuso;

    private String name;

    private String target;

    private String comment;

    @Column(name = "limit_at")
    private String limitAt;

    @Column(name = "max_limit")
    private String maxLimit;

    private Long idEstacion;

    private Long reuso;

    public Long getReuso() {
        return reuso;
    }

    public void setReuso(Long reuso) {
        this.reuso = reuso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTarifaReuso() {
        return idTarifaReuso;
    }

    public void setIdTarifaReuso(Long idTarifaReuso) {
        this.idTarifaReuso = idTarifaReuso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }
}
