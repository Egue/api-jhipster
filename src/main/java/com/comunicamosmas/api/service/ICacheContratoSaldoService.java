package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheContratoSaldo;
import java.util.List;

public interface ICacheContratoSaldoService {
    //listar todos
    public List<CacheContratoSaldo> findAll();

    //guardar
    public void save(CacheContratoSaldo cacheContratoSaldo);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public CacheContratoSaldo findById(Long id);
}
