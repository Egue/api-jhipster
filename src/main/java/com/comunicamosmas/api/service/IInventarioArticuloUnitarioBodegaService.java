package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticuloUnitarioBodega;
import java.util.List;

public interface IInventarioArticuloUnitarioBodegaService {
    //listar todos
    public List<InventarioArticuloUnitarioBodega> findAll();

    //guardar
    public InventarioArticuloUnitarioBodega save(InventarioArticuloUnitarioBodega inventarioArticuloUnitarioBodega);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioArticuloUnitarioBodega findById(Long id);
}
