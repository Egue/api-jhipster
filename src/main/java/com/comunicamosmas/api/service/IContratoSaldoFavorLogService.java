package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.ContratoSaldoFavorLog;
import java.util.List;

public interface IContratoSaldoFavorLogService {
    //listar todos
    public List<ContratoSaldoFavorLog> findAll();

    //guardar
    public void save(ContratoSaldoFavorLog contratoSaldoFavorLog);

    public void addSaldoBySupergiros(Contrato contrato , Float valor , String turno , String fecha);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoSaldoFavorLog findById(Long id);
}
