package com.comunicamosmas.api.web.rest.vm;

import java.io.Serializable;

public class ClassHablame implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String toNumber;

    private String sms;

    private String flash;

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }
}
