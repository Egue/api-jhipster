package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenEstado;
import java.util.List;

public interface IOrdenEstadoService {
    //listar todos
    public List<OrdenEstado> findAll();

    //guardar
    public OrdenEstado save(OrdenEstado ordenEstado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenEstado findById(Long id);
}
