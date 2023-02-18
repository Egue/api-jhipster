package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.DireccionAux;
import java.util.List;

public interface IDireccionAuxService {
    //listar todos
    public List<DireccionAux> findAll();

    //guardar
    public DireccionAux save(DireccionAux direccionAux);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public DireccionAux findById(Long id);
}
