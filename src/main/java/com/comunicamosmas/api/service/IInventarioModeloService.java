package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioModelo;
import java.util.List;

public interface IInventarioModeloService {
    //listar todos
    public List<InventarioModelo> findAll();

    //guardar
    public void save(InventarioModelo inventarioModelo);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public InventarioModelo findById(Long id);

    public List<InventarioModelo> findByIdModelo(Long id);
}
