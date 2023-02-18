package com.comunicamosmas.api.web.rest.vm;

import java.io.Serializable;

public class ClassError implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean error;

    private String msm;

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsm() {
        return msm;
    }

    public void setMsm(String msm) {
        this.msm = msm;
    }
}
