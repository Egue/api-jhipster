package com.comunicamosmas.api.service.dto;

import java.util.List;

public class OrdesQueryDTO {
    
    private Long status;

    private List<Long> services;

    private String dateStart;

    private String dateLast;

    private Long type;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<Long> getServices() {
        return services;
    }

    public void setServices(List<Long> services) {
        this.services = services;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateLast() {
        return dateLast;
    }

    public void setDateLast(String dateLast) {
        this.dateLast = dateLast;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    

}
