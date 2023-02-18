package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SoporteTicketTipo;
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
}
