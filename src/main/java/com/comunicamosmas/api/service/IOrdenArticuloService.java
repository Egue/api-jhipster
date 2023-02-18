package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenArticulo;
import java.util.List;

public interface IOrdenArticuloService {
    //listar todos
    public List<OrdenArticulo> findAll();

    //guardar
    public OrdenArticulo save(OrdenArticulo ordenArticulo);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenArticulo findById(Long id);
}
