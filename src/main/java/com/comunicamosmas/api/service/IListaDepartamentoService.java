package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ListaDepartamento;
import java.util.List;

public interface IListaDepartamentoService {
    //listar todos
    public List<ListaDepartamento> findAll();

    //guardar
    public ListaDepartamento save(ListaDepartamento listaDepartamento);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ListaDepartamento findById(Long id);

    public List<ListaDepartamento> findByEstado(Long Estado);
}
