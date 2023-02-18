package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticuloUnitarioCliente;
import java.util.List;

public interface IInventarioArticuloUnitarioClienteService {
    //listar todos
    public List<InventarioArticuloUnitarioCliente> findAll();

    //guardar
    public InventarioArticuloUnitarioCliente save(InventarioArticuloUnitarioCliente inventarioArticuloUnitarioCliente);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioArticuloUnitarioCliente findById(Long id);
}
