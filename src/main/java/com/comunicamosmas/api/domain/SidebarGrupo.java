package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jhipster_grupo")
@Entity
public class SidebarGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "grupo_name")
    private String grupoName;

    @Column(name = "grupo_icon")
    private String grupoIcon;

    public Long getId() {
        return id;
    }

    public String getGrupoName() {
        return grupoName;
    }

    public void setGrupoName(String grupoName) {
        this.grupoName = grupoName;
    }

    public String getGrupoIcon() {
        return grupoIcon;
    }

    public void setGrupoIcon(String grupoIcon) {
        this.grupoIcon = grupoIcon;
    }
}
