package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.PagoLineaVersionDos;
import java.util.List;

public interface IPagoLineaVersionDosService {
    //listar todos
    public List<PagoLineaVersionDos> findAll();

    //guardar
    public PagoLineaVersionDos save(PagoLineaVersionDos pagoLineaVersionDos);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public PagoLineaVersionDos findById(Long id);
}
