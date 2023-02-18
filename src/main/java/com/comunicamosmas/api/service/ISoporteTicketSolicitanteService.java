package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SoporteTicketSolicitante;
import java.util.List;

public interface ISoporteTicketSolicitanteService {
    //listar todos
    public List<SoporteTicketSolicitante> findAll();

    //guardar
    public SoporteTicketSolicitante save(SoporteTicketSolicitante soporteTicketSolicitante);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SoporteTicketSolicitante findById(Long id);
}
