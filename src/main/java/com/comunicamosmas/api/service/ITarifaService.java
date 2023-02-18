package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Tarifa;
import java.util.List;

public interface ITarifaService {
    //listar todos
    public List<Tarifa> findAll();

    //guardar
    public Tarifa save(Tarifa tarifa);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Tarifa findById(Long id);
}
