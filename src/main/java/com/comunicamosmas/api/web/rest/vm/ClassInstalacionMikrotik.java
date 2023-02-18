package com.comunicamosmas.api.web.rest.vm;

import java.io.Serializable;

public class ClassInstalacionMikrotik implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long idEstacion;

    private Long idIp;

    private Long idPlan;

    private Long contrato;

    public Long getContrato() {
        return contrato;
    }

    public void setContrato(Long contrato) {
        this.contrato = contrato;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getIdIp() {
        return idIp;
    }

    public void setIdIp(Long idIp) {
        this.idIp = idIp;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }
}
