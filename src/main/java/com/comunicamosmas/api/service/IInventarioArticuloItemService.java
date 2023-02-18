package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticuloItem;
import java.util.List;

public interface IInventarioArticuloItemService {
    //listar todos
    public List<InventarioArticuloItem> findAll();

    //guardar
    public InventarioArticuloItem save(InventarioArticuloItem inventarioArticuloItem);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioArticuloItem findById(Long id);
}
