package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioMarca;
import java.util.List;

public interface IInventarioMarcaService {
    //listar todos
    public List<InventarioMarca> findAll();

    //guardar
    public void save(InventarioMarca inventarioMarca);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public InventarioMarca findById(Long id);
}
