package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioBaja;
import java.util.List;

public interface IInventarioBajaService {
    //listar todos
    public List<InventarioBaja> findAll();

    //guardar
    public InventarioBaja save(InventarioBaja inventarioBaja);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioBaja findById(Long id);
}
