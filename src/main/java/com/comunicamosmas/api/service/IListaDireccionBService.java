package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ListaDireccionB;
import java.util.List;

public interface IListaDireccionBService {
    //listar todos
    public List<ListaDireccionB> findAll();

    //guardar
    public ListaDireccionB save(ListaDireccionB listaDireccionB);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ListaDireccionB findById(Long id);
}
