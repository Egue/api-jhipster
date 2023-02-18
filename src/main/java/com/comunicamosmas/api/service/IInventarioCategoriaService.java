package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioCategoria;
import java.util.List;

public interface IInventarioCategoriaService {
    //listar todos
    public List<InventarioCategoria> findAll();

    //guardar
    public void save(InventarioCategoria inventarioCategoria);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public InventarioCategoria findById(Long id);
}
