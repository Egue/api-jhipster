package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Pago;
import java.util.List;

public interface IPagoService {
    //listar todos
    public List<Pago> findAll();

    //guardar
    public Pago save(Pago pago);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Pago findById(Long id);
}
