package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sms_campanas")
@Entity
public class SmsCampana implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campana")
    private Long id;

    private String nombre;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private Long fechaf;

    private String marca;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "filtro_sql")
    private String filtro_sql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getFechaf() {
        return fechaf;
    }

    public void setFechaf(Long fechaf) {
        this.fechaf = fechaf;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getFiltro_sql() {
        return filtro_sql;
    }

    public void setFiltro_sql(String filtro_sql) {
        this.filtro_sql = filtro_sql;
    }
}
