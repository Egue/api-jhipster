package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Nivel;
import java.util.List;

public interface INivelService {
    //listar todos
    public List<Nivel> findAll();

    //guardar
    public Nivel save(Nivel nivel);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Nivel findById(Long id);
}
