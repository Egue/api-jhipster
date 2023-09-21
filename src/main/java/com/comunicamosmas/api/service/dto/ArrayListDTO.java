package com.comunicamosmas.api.service.dto;

import java.util.List;

public class ArrayListDTO {
    private List<Integer> servicios;

    private List<String> estados;

    

    public ArrayListDTO() {
    }

    public List<Integer> getServicios() {
        return servicios;
    }

    public void setServicios(List<Integer> servicios) {
        this.servicios = servicios;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    

}
