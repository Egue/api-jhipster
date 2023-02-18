package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioCarga;
import java.util.List;

public interface IInventarioCargaService {
    //listar todos
    public List<InventarioCarga> findAll();

    //guardar
    public void save(InventarioCarga inventarioCarga);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public InventarioCarga findById(Long id);
}
