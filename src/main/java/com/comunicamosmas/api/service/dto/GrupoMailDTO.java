package com.comunicamosmas.api.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrupoMailDTO {
    @JsonProperty("empresa")
    private String empresa;

    @JsonProperty("grupos")
    private List<Grupos> grupos;
    

    public GrupoMailDTO() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<Grupos> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupos> grupos) {
        this.grupos = grupos;
    }

    public static class Grupos{
        @JsonProperty("name")
        private String name;

        @JsonProperty("correos")
        private List<Correo> correos;
        

        public Grupos() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Correo> getCorreos() {
            return correos;
        }

        public void setCorreos(List<Correo> correos) {
            this.correos = correos;
        }
        
    }

    public static class Correo{
        @JsonProperty("email")
        private String email;
        public Correo(){}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        
    }

}
