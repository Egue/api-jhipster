package com.comunicamosmas.api.service.dto;

import java.util.List;

public class HanRetirosDTOPOST {
    private String tipo;

    private String descripcion;

    private Long idUser;

    private Long idContrato;

    private String estado;

    private Long idPadre;

    private List<GrupoMailDTO.Grupos> selectedGrupo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<GrupoMailDTO.Grupos> getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(List<GrupoMailDTO.Grupos> selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    

}
