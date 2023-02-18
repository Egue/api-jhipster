package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticulo;
import java.util.List;

public interface IInventarioArticuloService {
    //listar todos
    public List<InventarioArticulo> findAll(InventarioArticulo inventarioArticulo);

    //guardar
    public void save(InventarioArticulo inventarioArticulo);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public InventarioArticulo findById(Long id);

    public List<InventarioArticulo> findInventarioArticuloByIdCategoria(Long id);
}
