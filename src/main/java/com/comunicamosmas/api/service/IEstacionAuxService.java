package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.EstacionAux;
import java.util.List;

public interface IEstacionAuxService {
    //listar todos
    public List<EstacionAux> findAll();

    //guardar
    public void save(EstacionAux estacionAux);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public EstacionAux findById(Long id);

    public List<EstacionAux> findByIdEstacion(Long id);
}
