package com.comunicamosmas.api.service.dto;

import java.util.List;

public class AnoWithListIntegerDTO {
    
    private Integer ano;

    private List<Integer> arrayInteger;

    public AnoWithListIntegerDTO() {
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<Integer> getArrayInteger() {
        return arrayInteger;
    }

    public void setArrayInteger(List<Integer> arrayInteger) {
        this.arrayInteger = arrayInteger;
    }

    
}
