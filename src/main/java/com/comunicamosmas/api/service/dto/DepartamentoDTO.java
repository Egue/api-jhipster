package com.comunicamosmas.api.service.dto;
import java.util.List;
public class DepartamentoDTO {
    
    private Integer id;

    private String departamento;

    private List<Municipio> municipio;

    private Integer estado;

    

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getDepartamento() {
        return departamento;
    }



    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }



    public List<Municipio> getMunicipio() {
        return municipio;
    }



    public void setMunicipio(List<Municipio> municipio) {
        this.municipio = municipio;
    }



    public Integer getEstado() {
        return estado;
    }



    public void setEstado(Integer estado) {
        this.estado = estado;
    }



    public class Municipio{

        private Integer id;

        private String municipio;

        private Integer estado;

        private Integer dian;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMunicipio() {
            return municipio;
        }

        public void setMunicipio(String municipio) {
            this.municipio = municipio;
        }

        public Integer getEstado() {
            return estado;
        }

        public void setEstado(Integer estado) {
            this.estado = estado;
        }

        public Integer getDian() {
            return dian;
        }

        public void setDian(Integer dian) {
            this.dian = dian;
        }

        
    }
}
