package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoLogTraslado;
import java.util.List;

public interface IContratoLogTrasladoService {
    //listar todos
    public List<ContratoLogTraslado> findAll();

    //guardar
    public ContratoLogTraslado save(ContratoLogTraslado contratoLogTraslado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoLogTraslado findById(Long id);
}
