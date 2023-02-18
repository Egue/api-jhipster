package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticuloUnitarioEmpleado;
import java.util.List;

public interface IInventarioArticuloUnitarioEmpleadoService {
    //listar todos
    public List<InventarioArticuloUnitarioEmpleado> findAll();

    //guardar
    public InventarioArticuloUnitarioEmpleado save(InventarioArticuloUnitarioEmpleado inventarioArticuloUnitarioEmpleado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioArticuloUnitarioEmpleado findById(Long id);
}
