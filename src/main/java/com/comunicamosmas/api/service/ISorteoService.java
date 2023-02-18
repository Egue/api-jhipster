package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Sorteo;
import java.util.List;

public interface ISorteoService {
    //listar todos
    public List<Sorteo> findAll();

    //guardar
    public Sorteo save(Sorteo sorteo);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Sorteo findById(Long id);
}
