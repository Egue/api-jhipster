package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MedioPagoServicioCode;
import java.util.List;

public interface IMedioPagoServicioCodeService {
    //listar todos
    public List<MedioPagoServicioCode> findAll();

    //guardar
    public MedioPagoServicioCode save(MedioPagoServicioCode medioPagoServicioCode);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public MedioPagoServicioCode findById(Long id);
}
