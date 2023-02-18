package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Ciudad;
import java.util.List;

public interface ICiudadService {
    //listar todos
    public List<Ciudad> findAll();

    //guardar
    public Ciudad save(Ciudad ciudad);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Ciudad findById(Long id);
}
