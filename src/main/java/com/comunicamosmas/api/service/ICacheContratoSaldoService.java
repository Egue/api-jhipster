package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheContratoSaldo;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

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

    public List<ReciboCajaDTO> reporte_saldo_favor(List<Integer> ciudad , Integer fecha_inicio , Integer fecha_final , List<String> origen);
}
