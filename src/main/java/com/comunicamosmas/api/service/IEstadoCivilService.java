package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.EstadoCivil;
import java.util.List;

public interface IEstadoCivilService {
    //listar todos
    public List<EstadoCivil> findAll();

    //guardar
    public EstadoCivil save(EstadoCivil estadoCivil);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public EstadoCivil findById(Long id);
}
