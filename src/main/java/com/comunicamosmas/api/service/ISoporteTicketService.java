package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.SoporteTicket;
import com.comunicamosmas.api.service.dto.SoporteTicketDTO;

public interface ISoporteTicketService {
    //listar todos
    public List<SoporteTicket> findAll();

    //guardar
    public SoporteTicket save(SoporteTicket soporteTicket);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SoporteTicket findById(Long id);


    //reporte ticket
    public List<SoporteTicketDTO> reporteTicket(Long tipo , Long inicio , Long finalf);
}
