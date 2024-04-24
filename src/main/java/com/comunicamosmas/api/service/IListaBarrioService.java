package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ListaBarrio;
import java.util.List;

public interface IListaBarrioService {
    //listar todos
    public List<ListaBarrio> findAll();

    //guardar
    public ListaBarrio save(ListaBarrio listaBarrio);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ListaBarrio findById(Long id);

    //findByMunicipio
    public List<ListaBarrio> findByIdMunicipio(Long idMunicipio);
}
