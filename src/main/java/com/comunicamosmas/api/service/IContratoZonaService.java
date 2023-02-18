package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoZona;
import java.util.List;

public interface IContratoZonaService {
    //listar todos
    public List<ContratoZona> findAll();

    //guardar
    public ContratoZona save(ContratoZona contratoZona);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoZona findById(Long id);
}
