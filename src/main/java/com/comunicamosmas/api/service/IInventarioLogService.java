package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioLog;
import java.util.List;

public interface IInventarioLogService {
    //listar todos
    public List<InventarioLog> findAll();

    //guardar
    public InventarioLog save(InventarioLog inventarioLog);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioLog findById(Long id);
}
