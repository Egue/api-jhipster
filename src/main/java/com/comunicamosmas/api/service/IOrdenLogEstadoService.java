package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenLogEstado;
import java.util.List;

public interface IOrdenLogEstadoService {
    //listar todos
    public List<OrdenLogEstado> findAll();

    //guardar
    public OrdenLogEstado save(OrdenLogEstado ordenLogEstado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenLogEstado findById(Long id);
}
