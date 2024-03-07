package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MedioPago;
import java.util.List;
import java.util.Optional;

public interface IMedioPagoService {
    //listar todos
    public List<MedioPago> findAll();

    //guardar
    public MedioPago save(MedioPago medioPago);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public MedioPago findById(Long id);
}
