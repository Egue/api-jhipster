package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioAlmacenP;
import java.util.List;

public interface IInventarioAlmacenPService {
    //listar todos
    public List<InventarioAlmacenP> findAll();

    //guardar
    public InventarioAlmacenP save(InventarioAlmacenP inventarioAlmacenP);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioAlmacenP findById(Long id);
}
