package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Resolucion;
import java.util.List;

public interface IResolucionService {
    //listar todos
    public List<Resolucion> findAll();

    //guardar
    public Resolucion save(Resolucion resolucion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Resolucion findById(Long id);
}
