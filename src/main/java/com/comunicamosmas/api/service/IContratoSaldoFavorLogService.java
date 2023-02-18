package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoSaldoFavorLog;
import java.util.List;

public interface IContratoSaldoFavorLogService {
    //listar todos
    public List<ContratoSaldoFavorLog> findAll();

    //guardar
    public ContratoSaldoFavorLog save(ContratoSaldoFavorLog contratoSaldoFavorLog);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoSaldoFavorLog findById(Long id);
}
