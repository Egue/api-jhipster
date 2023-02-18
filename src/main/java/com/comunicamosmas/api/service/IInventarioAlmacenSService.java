package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioAlmacenS;
import java.util.List;

public interface IInventarioAlmacenSService {
    //listar todos
    public List<InventarioAlmacenS> findAll();

    //guardar
    public InventarioAlmacenS save(InventarioAlmacenS inventarioAlmacenS);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioAlmacenS findById(Long id);
}
