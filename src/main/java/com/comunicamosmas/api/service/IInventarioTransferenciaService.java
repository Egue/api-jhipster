package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioTransferencia;
import java.util.List;

public interface IInventarioTransferenciaService {
    //listar todos
    public List<InventarioTransferencia> findAll();

    //guardar
    public InventarioTransferencia save(InventarioTransferencia inventarioTransferencia);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioTransferencia findById(Long id);
}
