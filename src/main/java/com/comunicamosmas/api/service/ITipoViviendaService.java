package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TipoVivienda;
import java.util.List;

public interface ITipoViviendaService {
    //listar todos
    public List<TipoVivienda> findAll();

    //guardar
    public TipoVivienda save(TipoVivienda tipoVivienda);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TipoVivienda findById(Long id);
}
