package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioTransferenciaDetalle;
import java.util.List;

public interface IInventarioTransferenciaDetalleService {
    //listar todos
    public List<InventarioTransferenciaDetalle> findAll();

    //guardar
    public InventarioTransferenciaDetalle save(InventarioTransferenciaDetalle inventarioTransferenciaDetalle);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioTransferenciaDetalle findById(Long id);
}
