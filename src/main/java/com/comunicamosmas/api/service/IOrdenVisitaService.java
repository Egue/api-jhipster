package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenVisita;
import java.util.List;

public interface IOrdenVisitaService {
    //listar todos
    public List<OrdenVisita> findAll();

    //guardar
    public OrdenVisita save(OrdenVisita ordenVisita);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenVisita findById(Long id);
}
