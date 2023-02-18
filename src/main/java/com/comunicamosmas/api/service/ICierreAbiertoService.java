package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CierreAbierto;
import java.util.List;

public interface ICierreAbiertoService {
    //listar todos
    public List<CierreAbierto> findAll();

    //guardar
    public CierreAbierto save(CierreAbierto cierreAbierto);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public CierreAbierto findById(Long id);
}
