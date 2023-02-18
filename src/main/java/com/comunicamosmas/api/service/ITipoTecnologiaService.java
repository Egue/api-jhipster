package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TipoTecnologia;
import java.util.List;

public interface ITipoTecnologiaService {
    //listar todos
    public List<TipoTecnologia> findAll();

    //guardar
    public TipoTecnologia save(TipoTecnologia tipoTecnologia);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TipoTecnologia findById(Long id);
}
