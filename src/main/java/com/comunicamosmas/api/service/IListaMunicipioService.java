package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ListaMunicipio;
import java.util.List;

public interface IListaMunicipioService {
    //listar todos
    public List<ListaMunicipio> findAll();

    //guardar
    public ListaMunicipio save(ListaMunicipio listaMunicipio);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ListaMunicipio findById(Long id);
}
