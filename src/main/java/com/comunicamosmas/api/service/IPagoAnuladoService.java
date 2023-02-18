package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.PagoAnulado;
import java.util.List;

public interface IPagoAnuladoService {
    //listar todos
    public List<PagoAnulado> findAll();

    //guardar
    public PagoAnulado save(PagoAnulado pagoAnulado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public PagoAnulado findById(Long id);
}
