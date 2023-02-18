package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticuloUnitarioEstacion;
import java.util.List;

public interface IInventarioArticuloUnitarioEstacionService {
    //listar todos
    public List<InventarioArticuloUnitarioEstacion> findAll();

    //guardar
    public InventarioArticuloUnitarioEstacion save(InventarioArticuloUnitarioEstacion inventarioArticuloUnitarioEstacion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioArticuloUnitarioEstacion findById(Long id);
}
