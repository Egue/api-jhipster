package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.PagoLinea;
import java.util.List;

public interface IPagoLineaService {
    //listar todos
    public List<PagoLinea> findAll();

    //guardar
    public PagoLinea save(PagoLinea pagoLinea);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public PagoLinea findById(Long id);
}
