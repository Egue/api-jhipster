package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoHistoricoEstado;
import java.util.List;

public interface IContratoHistoricoEstadoService {
    //listar todos
    public List<ContratoHistoricoEstado> findAll();

    //guardar
    public ContratoHistoricoEstado save(ContratoHistoricoEstado contratoHistoricoEstado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoHistoricoEstado findById(Long id);
}
