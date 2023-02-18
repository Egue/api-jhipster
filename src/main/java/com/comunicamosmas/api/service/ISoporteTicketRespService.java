package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SoporteTicketResp;
import java.util.List;

public interface ISoporteTicketRespService {
    //listar todos
    public List<SoporteTicketResp> findAll();

    //guardar
    public SoporteTicketResp save(SoporteTicketResp soporteTicketResp);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SoporteTicketResp findById(Long id);
}
