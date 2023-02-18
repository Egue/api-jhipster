package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Zona;
import java.util.List;

public interface IZonaService {
    //listar todos
    public List<Zona> findAll();

    //guardar
    public Zona save(Zona zona);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Zona findById(Long id);
}
