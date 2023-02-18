package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SoporteTicket;
import java.util.List;

public interface ISoporteTicketService {
    //listar todos
    public List<SoporteTicket> findAll();

    //guardar
    public SoporteTicket save(SoporteTicket soporteTicket);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SoporteTicket findById(Long id);
}
