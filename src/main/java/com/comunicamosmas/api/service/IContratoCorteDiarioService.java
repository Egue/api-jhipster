package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoCorteDiario;
import java.util.List;

public interface IContratoCorteDiarioService {
    //listar todos
    public List<ContratoCorteDiario> findAll();

    //guardar
    public ContratoCorteDiario save(ContratoCorteDiario contratoCorteDiario);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoCorteDiario findById(Long id);
}
