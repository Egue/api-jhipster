package com.comunicamosmas.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "soporte_tickets")
@Entity
public class SoporteTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long id;

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_solicitante")
    private Long idSolicitante;

    private String causa;

    @Column(name = "id_ticket_acumula")
    private String idTicketAcumula;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "id_cliente")
    private Long idCliente;

    private String marca;

    @Column(name = "id_usuario_registra")
    private Long idEstacidUsuarioRegistra;

    private Long estado;

    @Column(name = "id_tipo_soporte")
    private Long idTipoSoporte;

    private Long fechaf;

    private String anexo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getIdTicketAcumula() {
        return idTicketAcumula;
    }

    public void setIdTicketAcumula(String idTicketAcumula) {
        this.idTicketAcumula = idTicketAcumula;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getIdEstacidUsuarioRegistra() {
        return idEstacidUsuarioRegistra;
    }

    public void setIdEstacidUsuarioRegistra(Long idEstacidUsuarioRegistra) {
        this.idEstacidUsuarioRegistra = idEstacidUsuarioRegistra;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getIdTipoSoporte() {
        return idTipoSoporte;
    }

    public void setIdTipoSoporte(Long idTipoSoporte) {
        this.idTipoSoporte = idTipoSoporte;
    }

    public Long getFechaf() {
        return fechaf;
    }

    public void setFechaf(Long fechaf) {
        this.fechaf = fechaf;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }
}
