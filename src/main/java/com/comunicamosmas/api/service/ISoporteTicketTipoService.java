package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SoporteTicketTipo;
import com.comunicamosmas.api.service.dto.TicketTipoDTO;

import java.util.List;

public interface ISoporteTicketTipoService {
    //listar todos
    public List<SoporteTicketTipo> findAll();

    //guardar
    public SoporteTicketTipo save(SoporteTicketTipo soporteTicketTipo);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SoporteTicketTipo findById(Long id);

    //buscar por servicio
    public List<TicketTipoDTO> findByIdServicio(Long idServicio);
}
