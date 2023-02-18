package com.comunicamosmas.api.web.rest.vm;

import java.io.Serializable;

public class ClassResponseHablame implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String status;

    private String account;

    private String smsId;

    private String execution_time;

    private String ip;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
