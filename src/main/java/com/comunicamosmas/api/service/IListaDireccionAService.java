package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ListaDireccionA;
import java.util.List;

public interface IListaDireccionAService {
    //listar todos
    public List<ListaDireccionA> findAll();

    //guardar
    public ListaDireccionA save(ListaDireccionA listaDireccionA);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ListaDireccionA findById(Long id);
}
